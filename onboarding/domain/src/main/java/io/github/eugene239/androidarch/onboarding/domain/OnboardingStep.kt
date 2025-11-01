package io.github.eugene239.androidarch.onboarding.domain

/**
 * Interface representing a single onboarding step/screen.
 * Onboarding screens should implement this interface without depending on navigation.
 */
interface OnboardingStep {
    /**
     * The order in which this step should be shown.
     * Steps with lower order values are shown first.
     */
    val order: Int

    /**
     * Checks whether this onboarding step should be shown.
     * @return true if the step should be displayed, false otherwise
     */
    suspend fun shouldShow(): Boolean

    /**
     * Marks this onboarding step as completed.
     * This method should be called when the user completes this step.
     */
    suspend fun complete()
}

