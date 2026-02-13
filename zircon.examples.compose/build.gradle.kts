import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm {
        mainRun {
            mainClass = "MainKt"
        }
    }

    sourceSets {
        jvmMain.dependencies {
            implementation(project(":zircon.core"))
            implementation(project(":zircon.compose"))

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "zircon-compose-example"
            packageVersion = "1.0.0"
        }
    }
}
