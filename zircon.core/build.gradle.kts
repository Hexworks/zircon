import Libraries.caffeine
import Libraries.cobaltCore
import Libraries.kotlinxCollectionsImmutable
import Libraries.kotlinxCoroutines
import Libraries.logbackClassic
import Libraries.slf4jApi
import Libraries.cache4k
import Libraries.snakeYaml
import Libraries.assertjCore
import Libraries.kotlinReflect
import Libraries.kotlinTestAnnotationsCommon
import Libraries.kotlinTestCommon
import Libraries.kotlinTestJs
import Libraries.kotlinTestJunit
import Libraries.logbackCore
import Libraries.mockitoCore
import Libraries.mockitoKotlin

import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka")
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

    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinxCoroutines)
                api(kotlinReflect)
                api(kotlinxCollectionsImmutable)

                api(cobaltCore)
                api(cache4k)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlinTestCommon)
                implementation(kotlinTestAnnotationsCommon)
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
                implementation(kotlinTestJunit)

                implementation(mockitoCore)
                implementation(mockitoKotlin)
                implementation(assertjCore)
                implementation(logbackClassic)
                implementation(logbackCore)
            }
        }

        val jsMain by getting {}
        val jsTest by getting {
            dependencies {
                implementation(kotlinTestJs)
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
