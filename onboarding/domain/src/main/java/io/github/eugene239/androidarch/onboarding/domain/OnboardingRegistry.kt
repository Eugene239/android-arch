package io.github.eugene239.androidarch.onboarding.domain

/**
 * Registry interface for managing onboarding steps.
 * Allows registering onboarding screen implementations without navigation dependencies.
 */
interface OnboardingRegistry {
    /**
     * Registers an onboarding step.
     * @param step The onboarding step to register
     */
    suspend fun register(step: OnboardingStep)

    /**
     * Registers multiple onboarding steps at once.
     * @param steps The list of onboarding steps to register
     */
    suspend fun register(steps: List<OnboardingStep>)

    /**
     * Marks an onboarding step as skipped.
     * Skipped steps will not be shown in the current session but will appear again next time.
     * @param step The onboarding step to skip
     */
    suspend fun skip(step: OnboardingStep)

    /**
     * Clears all skipped steps, allowing them to be shown again.
     */
    suspend fun clearSkipped()

    /**
     * Gets all onboarding steps that should be shown.
     * Steps are filtered by shouldShow(), excluding skipped steps, and sorted by order.
     * @return List of onboarding steps sorted by order, filtered by shouldShow() and excluding skipped steps
     */
    suspend fun getSteps(): List<OnboardingStep>
}

