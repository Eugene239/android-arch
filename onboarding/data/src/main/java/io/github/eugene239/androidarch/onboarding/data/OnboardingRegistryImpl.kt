package io.github.eugene239.androidarch.onboarding.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingRegistry
import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Thread-safe implementation of OnboardingRegistry using Mutex.
 * Stores onboarding steps internally and provides filtered, sorted access.
 */
class OnboardingRegistryImpl : OnboardingRegistry {

    private val steps: MutableList<OnboardingStep> = mutableListOf()
    private val skippedSteps: MutableSet<OnboardingStep> = mutableSetOf()

    private val mutex = Mutex()

    override suspend fun register(step: OnboardingStep) {
        mutex.withLock {
            steps.add(step)
        }
    }

    override suspend fun register(steps: List<OnboardingStep>) {
        mutex.withLock {
            this.steps.addAll(steps)
        }
    }

    override suspend fun skip(step: OnboardingStep) {
        mutex.withLock {
            skippedSteps.add(step)
        }
    }

    override suspend fun clearSkipped() {
        mutex.withLock {
            skippedSteps.clear()
        }
    }

    override suspend fun getSteps(): List<OnboardingStep> {
        return mutex.withLock {
            val filteredSteps = mutableListOf<OnboardingStep>()
            for (step in steps) {
                if (step !in skippedSteps && step.shouldShow()) {
                    filteredSteps.add(step)
                }
            }
            filteredSteps.sortedWith(OnboardingStepComparator)
        }
    }
}

