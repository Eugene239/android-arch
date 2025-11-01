package io.github.eugene239.androidarch.onboarding.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OnboardingStepComparatorTest {

    @Test
    fun `compare should return negative when first step has lower order`() {
        val step1 = createMockStep(order = 1)
        val step2 = createMockStep(order = 2)
        
        val result = OnboardingStepComparator.compare(step1, step2)
        
        assertTrue(result < 0)
    }

    @Test
    fun `compare should return positive when first step has higher order`() {
        val step1 = createMockStep(order = 2)
        val step2 = createMockStep(order = 1)
        
        val result = OnboardingStepComparator.compare(step1, step2)
        
        assertTrue(result > 0)
    }

    @Test
    fun `compare should return zero when steps have same order`() {
        val step1 = createMockStep(order = 1)
        val step2 = createMockStep(order = 1)
        
        val result = OnboardingStepComparator.compare(step1, step2)
        
        assertEquals(0, result)
    }

    private fun createMockStep(order: Int): OnboardingStep {
        return object : OnboardingStep {
            override val order: Int = order
            
            override suspend fun shouldShow(): Boolean = true
            
            override suspend fun complete() {
                // Mock implementation
            }
        }
    }
}

