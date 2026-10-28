pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
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
include(":zircon.examples")
include(":zircon.examples.desktop")
include(":zircon.examples.web")
