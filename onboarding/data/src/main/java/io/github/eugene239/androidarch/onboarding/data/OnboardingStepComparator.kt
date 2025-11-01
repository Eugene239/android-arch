package io.github.eugene239.androidarch.onboarding.data

import io.github.eugene239.androidarch.onboarding.domain.OnboardingStep

/**
 * Comparator for ordering onboarding steps by their order property.
 */
object OnboardingStepComparator : Comparator<OnboardingStep> {
    override fun compare(step1: OnboardingStep, step2: OnboardingStep): Int {
        return step1.order.compareTo(step2.order)
    }
}

