package io.github.eugene239.androidarch.userstate.domain

/**
 * Interface for checking user authorization status.
 * Checks storage (e.g., token presence) to determine if user is authorized.
 */
interface AuthorizationProvider {
    /**
     * Checks if user is currently authorized (e.g., has valid token).
     * May update token during the check if needed.
     * 
     * @return true if user is authorized, false otherwise
     */
    suspend fun isAuthorized(): Boolean
}

