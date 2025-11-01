package io.github.eugene239.androidarch.userstate.domain

/**
 * Interface for determining user state based on storage check (e.g., token presence).
 * Implementation should check storage (e.g., if user token exists) and return appropriate state.
 * Can also update token if needed during the check.
 * 
 * Note: This interface checks only authorization (token presence).
 * For complete state determination including onboarding, use UserStateManager.checkAndUpdateState().
 * 
 * Should return:
 * - AUTHENTICATION_REQUIRED if no token found
 * - AUTHENTICATED(isOnboarded = false) if token exists (meaning user is authorized, onboarding check will be done separately)
 */
interface UserStateProvider {
    /**
     * Determines the current user state by checking storage (e.g., token presence).
     * May update token during the check if needed.
     * 
     * @return The determined UserState based on storage check:
     *         - AUTHENTICATION_REQUIRED if user is not authorized
     *         - AUTHENTICATED(isOnboarded = false) if user is authorized (onboarding status should be checked separately)
     */
    suspend fun determineState(): UserState
}

