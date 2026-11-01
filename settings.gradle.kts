pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    // Lets Compose Hot Reload fetch the JetBrains Runtime it runs the app on.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "zircon"

include(":zircon.core")
include(":zircon.compose")
include(":zircon.terminal")
include(":zircon.examples")
include(":zircon.examples.compose")
include(":zircon.examples.terminal")
include(":zircon.examples.desktop")
include(":zircon.examples.web")
