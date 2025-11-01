package io.github.eugene239.androidarch.userstate.domain

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserStateExtensionsTest {

    @Test
    fun `onLogout should filter and execute action only for AUTHENTICATION_REQUIRED`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATION_REQUIRED,
            UserState.AUTHENTICATED(isOnboarded = false)
        )
            .onLogout { actionCalled = true }
            .toList()
        
        assertTrue(actionCalled)
        assertEquals(1, result.size)
        assertEquals(UserState.AUTHENTICATION_REQUIRED, result.first())
    }

    @Test
    fun `onAuthenticatedAndOnboarded should filter and execute action only for authenticated and onboarded`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = false),
            UserState.AUTHENTICATED(isOnboarded = true),
            UserState.AUTHENTICATION_REQUIRED
        )
            .onAuthenticatedAndOnboarded { actionCalled = true }
            .toList()
        
        assertTrue(actionCalled)
        assertEquals(1, result.size)
        assertEquals(UserState.AUTHENTICATED(isOnboarded = true), result.first())
    }

    @Test
    fun `onAuthenticatedNotOnboarded should filter and execute action only for authenticated but not onboarded`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = true),
            UserState.AUTHENTICATED(isOnboarded = false),
            UserState.AUTHENTICATION_REQUIRED
        )
            .onAuthenticatedNotOnboarded { actionCalled = true }
            .toList()
        
        assertTrue(actionCalled)
        assertEquals(1, result.size)
        assertEquals(UserState.AUTHENTICATED(isOnboarded = false), result.first())
    }

    @Test
    fun `onAuthenticated should filter and execute action for any authenticated state`() = runTest {
        val calledValues = mutableListOf<Boolean>()
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = false),
            UserState.AUTHENTICATED(isOnboarded = true),
            UserState.AUTHENTICATION_REQUIRED
        )
            .onAuthenticated { isOnboarded -> calledValues.add(isOnboarded) }
            .toList()
        
        assertEquals(2, result.size)
        assertEquals(UserState.AUTHENTICATED(isOnboarded = false), result[0])
        assertEquals(UserState.AUTHENTICATED(isOnboarded = true), result[1])
        assertEquals(listOf(false, true), calledValues)
    }

    @Test
    fun `onLogout should not execute action for other states`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = false),
            UserState.AUTHENTICATED(isOnboarded = true)
        )
            .onLogout { actionCalled = true }
            .toList()
        
        assertTrue(!actionCalled)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `onAuthenticatedAndOnboarded should not execute action for other states`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = false),
            UserState.AUTHENTICATION_REQUIRED
        )
            .onAuthenticatedAndOnboarded { actionCalled = true }
            .toList()
        
        assertTrue(!actionCalled)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `onAuthenticatedNotOnboarded should not execute action for other states`() = runTest {
        var actionCalled = false
        
        val result = flowOf(
            UserState.INITIAL,
            UserState.AUTHENTICATED(isOnboarded = true),
            UserState.AUTHENTICATION_REQUIRED
        )
            .onAuthenticatedNotOnboarded { actionCalled = true }
            .toList()
        
        assertTrue(!actionCalled)
        assertTrue(result.isEmpty())
    }
}

