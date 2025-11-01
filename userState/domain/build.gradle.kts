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
extra["artifactId"] = "userstate-domain"
extra["pomName"] = "UserState Domain"
extra["pomDescription"] = "Domain layer for user state management"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}

// Apply shared publication configuration
apply(from = rootProject.file("gradle/maven-publish.gradle"))
