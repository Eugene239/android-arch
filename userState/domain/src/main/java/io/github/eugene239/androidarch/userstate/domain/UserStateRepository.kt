package io.github.eugene239.androidarch.userstate.domain

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user state.
 * Provides methods to observe and update the current user state.
 */
interface UserStateRepository {
    /**
     * Gets the current user state as a Flow.
     * @return Flow emitting the current UserState
     */
    fun getCurrentState(): Flow<UserState>

    /**
     * Updates the current user state.
     * @param newState The new state to set
     */
    fun updateState(newState: UserState)

    /**
     * Observes changes to the user state.
     * @return Flow that emits whenever the state changes
     */
    fun observeState(): Flow<UserState>

    /**
     * Gets the current state value synchronously.
     * @return Current UserState
     */
    fun getCurrentStateValue(): UserState
}

