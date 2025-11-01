package io.github.eugene239.androidarch.userstate.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach

/**
 * Extension functions for convenient subscription to UserState changes.
 * 
 * These functions filter the Flow to emit only specific states and execute 
 * an action when those states occur.
 */

/**
 * Filters the Flow to emit only when user is logged out (AUTHENTICATION_REQUIRED)
 * and executes the action.
 * 
 * @param action Block to execute when logout occurs
 * @return Flow that emits UserState.AUTHENTICATION_REQUIRED when logout happens
 */
fun Flow<UserState>.onLogout(action: suspend () -> Unit): Flow<UserState> {
    return this
        .filter { it == UserState.AUTHENTICATION_REQUIRED }
        .onEach { action() }
}

/**
 * Filters the Flow to emit only when user is authenticated and has completed onboarding.
 * 
 * @param action Block to execute when authenticated with completed onboarding
 * @return Flow that emits UserState.AUTHENTICATED(isOnboarded = true) when authenticated and onboarded
 */
fun Flow<UserState>.onAuthenticatedAndOnboarded(action: suspend () -> Unit): Flow<UserState> {
    return this
        .filter { it is UserState.AUTHENTICATED && it.isOnboarded }
        .onEach { action() }
}

/**
 * Filters the Flow to emit only when user is authenticated but has not completed onboarding.
 * 
 * @param action Block to execute when authenticated but not onboarded
 * @return Flow that emits UserState.AUTHENTICATED(isOnboarded = false) when authenticated but not onboarded
 */
fun Flow<UserState>.onAuthenticatedNotOnboarded(action: suspend () -> Unit): Flow<UserState> {
    return this
        .filter { it is UserState.AUTHENTICATED && !it.isOnboarded }
        .onEach { action() }
}

/**
 * Filters the Flow to emit only when user is authenticated (regardless of onboarding status).
 * 
 * @param action Block to execute when authenticated, receives isOnboarded flag
 * @return Flow that emits UserState.AUTHENTICATED when authenticated
 */
fun Flow<UserState>.onAuthenticated(action: suspend (Boolean) -> Unit): Flow<UserState> {
    return this
        .filter { it is UserState.AUTHENTICATED }
        .onEach { action((it as UserState.AUTHENTICATED).isOnboarded) }
}

