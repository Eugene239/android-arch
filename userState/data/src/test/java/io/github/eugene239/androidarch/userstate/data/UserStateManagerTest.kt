package io.github.eugene239.androidarch.userstate.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingRegistry
import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep
import io.github.eugene239.androidarch.userstate.domain.AuthorizationProvider
import io.github.eugene239.androidarch.userstate.domain.UserState
import io.github.eugene239.androidarch.userstate.domain.UserStateRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserStateManagerTest {

    @Test
    fun `transitionTo should update repository state`() {
        val repository = mockk<UserStateRepository>(relaxed = true)
        val authorizationProvider = mockk<AuthorizationProvider>()
        val manager = UserStateManager(repository, authorizationProvider)
        
        val result = manager.transitionTo(UserState.AUTHENTICATED(isOnboarded = true))
        
        assertEquals(true, result)
        coVerify { repository.updateState(UserState.AUTHENTICATED(isOnboarded = true)) }
    }

    @Test
    fun `checkAndUpdateState should set AUTHENTICATION_REQUIRED when not authorized`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true)
        val authorizationProvider = mockk<AuthorizationProvider> {
            coEvery { isAuthorized() } returns false
        }
        val manager = UserStateManager(repository, authorizationProvider)
        
        manager.checkAndUpdateState()
        
        coVerify { repository.updateState(UserState.AUTHENTICATION_REQUIRED) }
    }

    @Test
    fun `checkAndUpdateState should set AUTHENTICATED with isOnboarded=true when authorized and no onboarding`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true)
        val authorizationProvider = mockk<AuthorizationProvider> {
            coEvery { isAuthorized() } returns true
        }
        val manager = UserStateManager(repository, authorizationProvider)
        
        manager.checkAndUpdateState()
        
        coVerify { repository.updateState(UserState.AUTHENTICATED(isOnboarded = true)) }
    }

    @Test
    fun `checkAndUpdateState should set AUTHENTICATED with isOnboarded=false when authorized but has onboarding steps`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true)
        val authorizationProvider = mockk<AuthorizationProvider> {
            coEvery { isAuthorized() } returns true
        }
        val onboardingStep = mockk<OnboardingStep> {
            coEvery { shouldShow() } returns true
            coEvery { order } returns 1
        }
        val onboardingRegistry = mockk<OnboardingRegistry> {
            coEvery { getSteps() } returns listOf(onboardingStep)
        }
        val manager = UserStateManager(repository, authorizationProvider, onboardingRegistry)
        
        manager.checkAndUpdateState()
        
        coVerify { repository.updateState(UserState.AUTHENTICATED(isOnboarded = false)) }
    }

    @Test
    fun `logout should set AUTHENTICATION_REQUIRED`() {
        val repository = mockk<UserStateRepository>(relaxed = true)
        val authorizationProvider = mockk<AuthorizationProvider>()
        val manager = UserStateManager(repository, authorizationProvider)
        
        manager.logout()
        
        coVerify { repository.updateState(UserState.AUTHENTICATION_REQUIRED) }
    }

    @Test
    fun `checkOnboardingCompletion should update isOnboarded flag when no steps remain`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true) {
            every { getCurrentStateValue() } returns UserState.AUTHENTICATED(isOnboarded = false)
        }
        val authorizationProvider = mockk<AuthorizationProvider>()
        val onboardingRegistry = mockk<OnboardingRegistry> {
            coEvery { getSteps() } returns emptyList()
        }
        val manager = UserStateManager(repository, authorizationProvider, onboardingRegistry)
        
        manager.checkOnboardingCompletion()
        
        coVerify { repository.updateState(UserState.AUTHENTICATED(isOnboarded = true)) }
    }

    @Test
    fun `checkOnboardingCompletion should not change state when steps remain`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true) {
            every { getCurrentStateValue() } returns UserState.AUTHENTICATED(isOnboarded = false)
        }
        val authorizationProvider = mockk<AuthorizationProvider>()
        val onboardingStep = mockk<OnboardingStep> {
            coEvery { shouldShow() } returns true
            coEvery { order } returns 1
        }
        val onboardingRegistry = mockk<OnboardingRegistry> {
            coEvery { getSteps() } returns listOf(onboardingStep)
        }
        val manager = UserStateManager(repository, authorizationProvider, onboardingRegistry)
        
        manager.checkOnboardingCompletion()
        
        coVerify(exactly = 0) { repository.updateState(any()) }
    }

    @Test
    fun `checkOnboardingCompletion should not change state when user is not authenticated`() = runTest {
        val repository = mockk<UserStateRepository>(relaxed = true) {
            every { getCurrentStateValue() } returns UserState.AUTHENTICATION_REQUIRED
        }
        val authorizationProvider = mockk<AuthorizationProvider>()
        val onboardingRegistry = mockk<OnboardingRegistry>()
        val manager = UserStateManager(repository, authorizationProvider, onboardingRegistry)
        
        manager.checkOnboardingCompletion()
        
        coVerify(exactly = 0) { repository.updateState(any()) }
    }
}

