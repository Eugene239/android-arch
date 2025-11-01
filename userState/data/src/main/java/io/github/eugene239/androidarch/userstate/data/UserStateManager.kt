package io.github.eugene239.androidarch.userstate.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingRegistry
import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep
import io.github.eugene239.androidarch.userstate.domain.AuthorizationProvider
import io.github.eugene239.androidarch.userstate.domain.UserState
import io.github.eugene239.androidarch.userstate.domain.UserStateRepository

/**
 * Manages user state transitions and validation logic.
 */
class UserStateManager(
    private val repository: UserStateRepository,
    private val authorizationProvider: AuthorizationProvider,
    private val onboardingRegistry: OnboardingRegistry? = null
) {
    /**
     * Transitions from current state to a new state with validation.
     * @param newState The target state to transition to
     * @return true if transition was successful, false otherwise
     */
    fun transitionTo(newState: UserState): Boolean {
        repository.updateState(newState)
        return true
    }

    /**
     * Checks authorization and onboarding status, then updates state accordingly.
     * This method combines authorization check with onboarding check:
     * - If not authorized -> AUTHENTICATION_REQUIRED
     * - If authorized -> AUTHENTICATED(isOnboarded = true/false)
     */
    suspend fun checkAndUpdateState() {
        val isAuthorized = authorizationProvider.isAuthorized()

        if (!isAuthorized) {
            repository.updateState(UserState.AUTHENTICATION_REQUIRED)
            return
        }

        // User is authorized, check onboarding
        val onboardingSteps: List<OnboardingStep> = onboardingRegistry?.getSteps() ?: emptyList()
        val isOnboarded = onboardingSteps.isEmpty()
        repository.updateState(UserState.AUTHENTICATED(isOnboarded = isOnboarded))
    }

    /**
     * Logs out the user by transitioning to AUTHENTICATION_REQUIRED state.
     */
    fun logout() {
        repository.updateState(UserState.AUTHENTICATION_REQUIRED)
    }

    /**
     * Checks onboarding status after a step is completed.
     * Updates the isOnboarded flag if no more steps remain.
     * Should be called after completing an onboarding step.
     */
    suspend fun checkOnboardingCompletion() {
        val currentState = repository.getCurrentStateValue()
        if (currentState !is UserState.AUTHENTICATED) {
            return // Only update if user is already authenticated
        }

        val onboardingSteps: List<OnboardingStep> = onboardingRegistry?.getSteps() ?: emptyList()
        val isOnboarded = onboardingSteps.isEmpty()
        
        // Update state only if onboarding status changed
        if (currentState.isOnboarded != isOnboarded) {
            repository.updateState(UserState.AUTHENTICATED(isOnboarded = isOnboarded))
        }
    }
}

