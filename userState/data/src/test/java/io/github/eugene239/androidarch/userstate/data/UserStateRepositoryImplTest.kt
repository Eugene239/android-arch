package io.github.eugene239.androidarch.userstate.data

import io.github.eugene239.androidarch.userstate.domain.UserState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserStateRepositoryImplTest {

    @Test
    fun `initial state should be INITIAL`() = runTest {
        val repository = UserStateRepositoryImpl()
        
        val currentState = repository.getCurrentState().first()
        
        assertEquals(UserState.INITIAL, currentState)
    }

    @Test
    fun `initial state should be customizable`() = runTest {
        val repository = UserStateRepositoryImpl(UserState.AUTHENTICATION_REQUIRED)
        
        val currentState = repository.getCurrentState().first()
        
        assertEquals(UserState.AUTHENTICATION_REQUIRED, currentState)
    }

    @Test
    fun `updateState should change current state`() = runTest {
        val repository = UserStateRepositoryImpl()
        
        repository.updateState(UserState.AUTHENTICATED(isOnboarded = true))
        
        val currentState = repository.getCurrentState().first()
        assertEquals(UserState.AUTHENTICATED(isOnboarded = true), currentState)
    }

    @Test
    fun `observeState should emit state changes`() = runTest {
        val repository = UserStateRepositoryImpl()
        
        repository.updateState(UserState.AUTHENTICATION_REQUIRED)
        val state1 = repository.observeState().first()
        
        repository.updateState(UserState.AUTHENTICATED(isOnboarded = false))
        val state2 = repository.observeState().first()
        
        assertEquals(UserState.AUTHENTICATION_REQUIRED, state1)
        assertEquals(UserState.AUTHENTICATED(isOnboarded = false), state2)
    }

    @Test
    fun `getCurrentStateValue should return current state synchronously`() {
        val repository = UserStateRepositoryImpl()
        
        assertEquals(UserState.INITIAL, repository.getCurrentStateValue())
        
        repository.updateState(UserState.AUTHENTICATED(isOnboarded = true))
        
        assertEquals(UserState.AUTHENTICATED(isOnboarded = true), repository.getCurrentStateValue())
    }
}

