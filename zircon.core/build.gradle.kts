@file:Suppress("UnstableApiUsage")

import Libs.caffeine
import Libs.cobaltCore
import Libs.kotlinxCollectionsImmutable
import Libs.kotlinxCoroutines
import Libs.kotlinxCoroutinesTest
import Libs.logbackClassic
import Libs.slf4jApi
import Libs.snakeYaml
import TestLibs.assertjCore
import TestLibs.kotlinTestAnnotationsCommon
import TestLibs.kotlinTestCommon
import TestLibs.logbackCore
import TestLibs.mockitoCore
import TestLibs.mockitoKotlin

import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {

    jvm {
        withJava()
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("reflect"))

                api(kotlinxCoroutines)
                api(kotlinxCollectionsImmutable)

                api(cobaltCore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlinTestCommon)
                implementation(kotlinTestAnnotationsCommon)

                implementation(kotlinxCoroutinesTest)
            }
        }
        val jvmMain by getting {
            dependencies {
                api(kotlin("reflect"))

                api(caffeine)
                api(snakeYaml)
                api(slf4jApi)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))

                implementation(mockitoCore)
                implementation(mockitoKotlin)
                implementation(assertjCore)
                implementation(logbackClassic)
                implementation(logbackCore)
            }
        }
    }

}

tasks {
    create<DokkaTask>("dokkaHtmlAsJava") {
        val dokka_version: String by project
        dependencies {
            plugins("org.jetbrains.dokka:kotlin-as-java-plugin:$dokka_version")
        }
    }

    withType<DokkaTask>().configureEach {
        dokkaSourceSets {
            configureEach {
                includes.from("module.md", "packages.md")
                samples.from("src/commonMain/kotlin/org/hexworks/zircon/samples")

                sourceLink {
                    localDirectory.set(file("src/commonMain/kotlin"))
                    remoteUrl.set(URL("https://github.com/Hexworks/zircon/tree/master/zircon.core/src/commonMain/kotlin"))
                }

                sourceLink {
                    localDirectory.set(file("src/jvmMain/kotlin"))
                    remoteUrl.set(URL("https://github.com/Hexworks/zircon/tree/master/zircon.core/src/jvmMain/kotlin"))
                }
                jdkVersion.set(8)
            }
        }
    }
}

publishing {
    publishWith(
            project = project,
            module = "zircon.core",
            desc = "Core component of Zircon."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}
