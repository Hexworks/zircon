import Libraries.ASSERTJ_CORE
import Libraries.COBALT_CORE
import Libraries.KORGE
import Libraries.KOTLIN_REFLECT
import Libraries.KOTLIN_TEST_ANNOTATIONS_COMMON
import Libraries.KOTLIN_TEST_COMMON
import Libraries.KOTLIN_TEST_JS
import Libraries.KOTLIN_TEST_JUNIT
import Libraries.KOTLINX_COLLECTIONS_IMMUTABLE
import Libraries.KOTLINX_COROUTINES
import Libraries.MOCKITO_CORE
import Libraries.MOCKITO_KOTLIN
import Libraries.SNAKE_YAML

plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
}

val javaVersion = JavaVersion.VERSION_11

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {

    jvm {
        withJava()
        compilations.all {
            kotlinOptions {
                apiVersion = "1.9"
                languageVersion = "1.9"
                jvmTarget = javaVersion.toString()

            }
        }
    }

    js(IR) {
        compilations.all {
            kotlinOptions {
                sourceMap = true
                moduleKind = "umd"
                metaInfo = true
            }
        }
        browser {
            testTask {
                useMocha()
            }
        }
        nodejs()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(KOTLINX_COROUTINES)
                api(KOTLIN_REFLECT)
                api(KOTLINX_COLLECTIONS_IMMUTABLE)

                api(KORGE)

                api(COBALT_CORE)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(KOTLIN_TEST_COMMON)
                implementation(KOTLIN_TEST_ANNOTATIONS_COMMON)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(kotlin("reflect"))

                api(SNAKE_YAML)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(KOTLIN_TEST_JUNIT)

                implementation(MOCKITO_CORE)
                implementation(MOCKITO_KOTLIN)
                implementation(ASSERTJ_CORE)
            }
        }

        val jsMain by getting {}
        val jsTest by getting {
            dependencies {
                implementation(KOTLIN_TEST_JS)
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
