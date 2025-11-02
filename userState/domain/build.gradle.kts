plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.eugene239.androidarch"
version = "1.0.0"

kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())
}

// Set publication metadata (for gradle/maven-publish.gradle)
project.ext.set("artifactId", "userstate-domain")
project.ext.set("pomName", "UserState Domain")
project.ext.set("pomDescription", "Domain layer for user state management")

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}

// Maven Central publishing configuration is in gradle/maven-publish.gradle
apply(from = rootProject.file("gradle/maven-publish.gradle"))
