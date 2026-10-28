import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.hexworks.zircon"
version = "2.0.0"

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    // androidTarget() arrives with M5 (needs the Android SDK on the build machine).

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.core"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
        }
        // Shared by every Skiko-backed target (desktop, web): the SkSL tile shader.
        val skikoMain by creating {
            dependsOn(commonMain.get())
        }
        jvmMain.get().dependsOn(skikoMain)
        wasmJsMain.get().dependsOn(skikoMain)

        jvmMain.dependencies {
            implementation(compose.desktop.common)
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
    publicResClass = true
    packageOfResClass = "org.hexworks.zircon.compose.resources"
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }

    coordinates(group.toString(), "zircon.compose", version.toString())

    pom {
        name = "zircon.compose"
        description = "Compose Multiplatform renderer for Zircon"
        inceptionYear = "2018"
        url = "https://github.com/Hexworks/zircon"
        licenses {
            license {
                name = "The Apache Software License, Version 2.0 "
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "adam-arold"
                name = "Adam Arold"
                url = "https://github.com/adam-arold"
            }
        }
        scm {
            url = "https://github.com/Hexworks/zircon.git"
            connection = "git@github.com:Hexworks/zircon.git"
            developerConnection = "git@github.com:Hexworks/zircon.git"
        }
    }
}
