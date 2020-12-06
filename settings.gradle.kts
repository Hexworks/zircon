enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    resolutionStrategy {
        val dokka_version: String by settings
        eachPlugin {
            if (requested.id.id == "org.jetbrains.dokka") {
                useModule("org.jetbrains.dokka:dokka-gradle-plugin:$dokka_version")
            }
        }
    }
}

rootProject.name = "zircon"

include(":zircon.core")
include(":zircon.jvm.swing")
include(":zircon.jvm.libgdx")
include(":zircon.jvm.examples")
