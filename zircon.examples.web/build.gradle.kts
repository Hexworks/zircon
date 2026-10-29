import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "zircon-examples.js"
            }
        }
        binaries.executable()
    }
    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":zircon.examples.compose"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }
    }
}
