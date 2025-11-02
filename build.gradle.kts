plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.nexus.publish) apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

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