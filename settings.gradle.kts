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
include(":zircon.terminal")
include(":zircon.examples")
include(":zircon.examples.compose")
include(":zircon.examples.terminal")
include(":zircon.examples.desktop")
include(":zircon.examples.web")
