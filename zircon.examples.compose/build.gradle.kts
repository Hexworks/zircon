import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

// The Compose side of the demos: the `ExamplesApp` composable and the demo data files as
// Compose resources. Shared by the desktop and web launchers; the terminal launcher reads
// the same files from disk.
kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask { enabled = false }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.examples"))
            api(project(":zircon.compose"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions.core)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "org.hexworks.zircon.examples.resources"
}

// Library module without wasm tests; see the same opt-out in zircon.compose (CMP-4906).
tasks.matching { it.name == "checkComposeUiTestConfigurationForWasmJs" }.configureEach { enabled = false }

// The demo data files are shared with the terminal launcher, so they live outside this
// module's source tree.
compose.resources {
    customDirectory(
        sourceSetName = "commonMain",
        directoryProvider = provider { layout.projectDirectory.dir("../zircon.examples/resources") },
    )
}
