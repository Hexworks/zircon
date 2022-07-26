import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

plugins {
    id("org.jetbrains.dokka")
    id("org.sonarqube") version "3.3"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlinx")
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "Hexworks_zircon")
        property("sonar.organization", "hexworks")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
