plugins {
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
    signing
}

group = "io.github.eugene239.androidarch"
version = "1.0.0"

kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())
}

// Set publication metadata
extra["artifactId"] = "onboarding-domain"
extra["pomName"] = "Onboarding Domain"
extra["pomDescription"] = "Domain layer for onboarding feature"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

// Apply shared publication configuration
apply(from = rootProject.file("gradle/maven-publish.gradle"))
