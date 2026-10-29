import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// The demos as plain Zircon code, usable by every launcher: Compose (desktop, web) through
// zircon.examples.compose, and the terminal through zircon.examples.terminal. The demo data
// files live in `files/` next to this build file.
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
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.core"))
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions.core)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
