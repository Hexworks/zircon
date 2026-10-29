plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.binary.compatibility.validator)
}

apiValidation {
    ignoredProjects += listOf(
        "zircon.examples", "zircon.examples.compose", "zircon.examples.desktop", "zircon.examples.web", "zircon.examples.terminal",
    )
}
