package io.github.eugene239.androidarch.userstate.domain

/**
 * Represents the current state of the user in the application.
 */
sealed class UserState {
    /**
     * Initial state when the app starts.
     */
    object INITIAL : UserState()

    /**
     * User authentication is required.
     */
    object AUTHENTICATION_REQUIRED : UserState()

    /**
     * User is authenticated.
     * @param isOnboarded Whether the user has completed onboarding
     */
    data class AUTHENTICATED(val isOnboarded: Boolean) : UserState()
}

