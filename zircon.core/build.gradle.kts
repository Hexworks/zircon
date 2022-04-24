@file:Suppress("UnstableApiUsage")

import Libraries.caffeine
import Libraries.cobaltCore
import Libraries.kotlinxCollectionsImmutable
import Libraries.kotlinxCoroutines
import Libraries.kotlinxCoroutinesTest
import Libraries.logbackClassic
import Libraries.slf4jApi
import Libraries.cache4k
import Libraries.snakeYaml
import Libraries.assertjCore
import Libraries.kotlinTestAnnotationsCommon
import Libraries.kotlinTestCommon
import Libraries.logbackCore
import Libraries.mockitoCore
import Libraries.mockitoKotlin

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
        compilations.all {
            kotlinOptions {
                apiVersion = "1.5"
                languageVersion = "1.5"
                jvmTarget = "11"
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("reflect"))

                api(kotlinxCoroutines)
                api(kotlinxCollectionsImmutable)

                api(cobaltCore)
                api(cache4k)
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
