package io.github.eugene239.androidarch.userstate.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Extension functions for lifecycle-aware UserState collection.
 * 
 * These functions automatically manage collection lifecycle using CoroutineScope,
 * similar to repeatOnLifecycle behavior. The collection starts when the function
 * is called and automatically stops when the scope is cancelled.
 * 
 * Usage example:
 * 
 * ```kotlin
 * // In ViewModel or similar
 * class MainViewModel : ViewModel() {
 *     private val userStateRepository: UserStateRepository = // ...
 *     
 *     init {
 *         userStateRepository.observeOnLogout(viewModelScope) {
 *             navigateToLogin()
 *         }
 *         
 *         userStateRepository.observeOnAuthenticatedAndOnboarded(viewModelScope) {
 *             loadUserData()
 *             showMainScreen()
 *         }
 *         
 *         userStateRepository.observeOnAuthenticatedNotOnboarded(viewModelScope) {
 *             showOnboarding()
 *         }
 *     }
 * }
 * ```
 */

/**
 * Observes user state and executes action when user logs out.
 * Collection automatically stops when the scope is cancelled.
 * 
 * @param scope The coroutine scope to launch collection in
 * @param action Block to execute when logout occurs
 * @return Job that can be cancelled to stop collection
 */
fun UserStateRepository.observeOnLogout(
    scope: CoroutineScope,
    action: suspend () -> Unit
): Job {
    return scope.launch {
        observeState()
            .onLogout(action)
            .collect()
    }
}

/**
 * Observes user state and executes action when user is authenticated and has completed onboarding.
 * Collection automatically stops when the scope is cancelled.
 * 
 * @param scope The coroutine scope to launch collection in
 * @param action Block to execute when authenticated and onboarded
 * @return Job that can be cancelled to stop collection
 */
fun UserStateRepository.observeOnAuthenticatedAndOnboarded(
    scope: CoroutineScope,
    action: suspend () -> Unit
): Job {
    return scope.launch {
        observeState()
            .onAuthenticatedAndOnboarded(action)
            .collect()
    }
}

/**
 * Observes user state and executes action when user is authenticated but has not completed onboarding.
 * Collection automatically stops when the scope is cancelled.
 * 
 * @param scope The coroutine scope to launch collection in
 * @param action Block to execute when authenticated but not onboarded
 * @return Job that can be cancelled to stop collection
 */
fun UserStateRepository.observeOnAuthenticatedNotOnboarded(
    scope: CoroutineScope,
    action: suspend () -> Unit
): Job {
    return scope.launch {
        observeState()
            .onAuthenticatedNotOnboarded(action)
            .collect()
    }
}

/**
 * Observes user state and executes action when user is authenticated (regardless of onboarding status).
 * Collection automatically stops when the scope is cancelled.
 * 
 * @param scope The coroutine scope to launch collection in
 * @param action Block to execute when authenticated, receives isOnboarded flag
 * @return Job that can be cancelled to stop collection
 */
fun UserStateRepository.observeOnAuthenticated(
    scope: CoroutineScope,
    action: suspend (Boolean) -> Unit
): Job {
    return scope.launch {
        observeState()
            .onAuthenticated(action)
            .collect()
    }
}

