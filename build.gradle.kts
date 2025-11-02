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
            // OSSRH endpoint (works with Central Portal User Token)
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            val usernameValue = project.findProperty("sonatypeUsername") as String?
            val passwordValue = project.findProperty("sonatypePassword") as String?
            
            if (usernameValue != null && passwordValue != null) {
                username.set(usernameValue)
                password.set(passwordValue)
                
                logger.info("Sonatype credentials configured: username length=${usernameValue.length}, password length=${passwordValue.length}")
            }
        }
    }
}