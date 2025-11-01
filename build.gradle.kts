plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.nexus.publish) apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

// Maven Central publication configuration via Sonatype Central Portal
// Requirements:
// 1. ✅ Namespace registered: io.github.eugene239 (verified)
// 2. GPG signing configured
// 3. Portal Token created in Central Portal (Tokens section)
// 4. Credentials added to gradle.properties:
//    sonatypeUsername=YOUR_USERNAME
//    sonatypePassword=YOUR_PASSWORD
//    signing.keyId=YOUR_GPG_KEY_ID (last 8 characters)
//    signing.password=YOUR_GPG_PASSWORD
//    Note: secretKeyRingFile not needed when using useGpgCmd()

apply(plugin = "io.github.gradle-nexus.publish-plugin")

configure<io.github.gradlenexus.publishplugin.NexusPublishExtension> {
    repositories {
        create("sonatype") {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            
            // Credentials from gradle.properties
            username.set(project.findProperty("sonatypeUsername") as String?)
            password.set(project.findProperty("sonatypePassword") as String?)
        }
    }
}