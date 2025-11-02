plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.eugene239.androidarch"
version = "1.0.0"

kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())
}

// Set publication metadata (for gradle/maven-publish.gradle)
project.ext.set("artifactId", "onboarding-domain")
project.ext.set("pomName", "Onboarding Domain")
project.ext.set("pomDescription", "Domain layer for onboarding feature")

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

// Maven Central publishing configuration is in gradle/maven-publish.gradle
apply(from = rootProject.file("gradle/maven-publish.gradle"))
