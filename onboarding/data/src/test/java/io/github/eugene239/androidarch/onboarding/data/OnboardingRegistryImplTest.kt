package io.github.eugene239.androidarch.onboarding.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnboardingRegistryImplTest {

    @Test
    fun `register should add single step`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step = createMockStep(order = 1, shouldShow = true)
        
        registry.register(step)
        
        val steps = registry.getSteps()
        assertEquals(1, steps.size)
        assertEquals(step, steps.first())
    }

    @Test
    fun `register should add multiple steps`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step1 = createMockStep(order = 1, shouldShow = true)
        val step2 = createMockStep(order = 2, shouldShow = true)
        
        registry.register(listOf(step1, step2))
        
        val steps = registry.getSteps()
        assertEquals(2, steps.size)
        assertEquals(step1, steps[0])
        assertEquals(step2, steps[1])
    }

    @Test
    fun `getSteps should return steps sorted by order`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step3 = createMockStep(order = 3, shouldShow = true)
        val step1 = createMockStep(order = 1, shouldShow = true)
        val step2 = createMockStep(order = 2, shouldShow = true)
        
        registry.register(listOf(step3, step1, step2))
        
        val steps = registry.getSteps()
        assertEquals(3, steps.size)
        assertEquals(step1, steps[0])
        assertEquals(step2, steps[1])
        assertEquals(step3, steps[2])
    }

    @Test
    fun `getSteps should filter steps by shouldShow`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step1 = createMockStep(order = 1, shouldShow = true)
        val step2 = createMockStep(order = 2, shouldShow = false)
        val step3 = createMockStep(order = 3, shouldShow = true)
        
        registry.register(listOf(step1, step2, step3))
        
        val steps = registry.getSteps()
        assertEquals(2, steps.size)
        assertEquals(step1, steps[0])
        assertEquals(step3, steps[1])
    }

    @Test
    fun `skip should exclude step from getSteps`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step1 = createMockStep(order = 1, shouldShow = true)
        val step2 = createMockStep(order = 2, shouldShow = true)
        
        registry.register(listOf(step1, step2))
        registry.skip(step1)
        
        val steps = registry.getSteps()
        assertEquals(1, steps.size)
        assertEquals(step2, steps[0])
    }

    @Test
    fun `clearSkipped should restore skipped steps`() = runTest {
        val registry = OnboardingRegistryImpl()
        val step1 = createMockStep(order = 1, shouldShow = true)
        val step2 = createMockStep(order = 2, shouldShow = true)
        
        registry.register(listOf(step1, step2))
        registry.skip(step1)
        registry.clearSkipped()
        
        val steps = registry.getSteps()
        assertEquals(2, steps.size)
    }

    @Test
    fun `getSteps should return empty list when no steps registered`() = runTest {
        val registry = OnboardingRegistryImpl()
        
        val steps = registry.getSteps()
        
        assertTrue(steps.isEmpty())
    }

    private fun createMockStep(order: Int, shouldShow: Boolean): OnboardingStep {
        return object : OnboardingStep {
            override val order: Int = order
            
            override suspend fun shouldShow(): Boolean = shouldShow
            
            override suspend fun complete() {
                // Mock implementation
            }
        }
    }
}

