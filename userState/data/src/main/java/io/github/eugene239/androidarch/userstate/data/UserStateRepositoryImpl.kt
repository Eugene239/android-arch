package io.github.eugene239.androidarch.userstate.data

import io.github.eugene239.androidarch.userstate.domain.UserState
import io.github.eugene239.androidarch.userstate.domain.UserStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementation of UserStateRepository using MutableStateFlow for state management.
 */
class UserStateRepositoryImpl(
    initialState: UserState = UserState.INITIAL
) : UserStateRepository {

    private val _currentState: MutableStateFlow<UserState> = MutableStateFlow(initialState)

    override fun getCurrentState(): Flow<UserState> {
        return _currentState.asStateFlow()
    }

    override fun updateState(newState: UserState) {
        _currentState.value = newState
    }

    override fun observeState(): Flow<UserState> {
        return _currentState.asStateFlow()
    }

    /**
     * Gets the current state value synchronously.
     * @return Current UserState
     */
    override fun getCurrentStateValue(): UserState {
        return _currentState.value
    }
}

