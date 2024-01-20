import Libraries.assertjCore
import Libraries.cobaltCore
import Libraries.korge
import Libraries.kotlinReflect
import Libraries.kotlinTestAnnotationsCommon
import Libraries.kotlinTestCommon
import Libraries.kotlinTestJs
import Libraries.kotlinTestJunit
import Libraries.kotlinxCollectionsImmutable
import Libraries.kotlinxCoroutines
import Libraries.mockitoCore
import Libraries.mockitoKotlin
import Libraries.snakeYaml

plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
}

kotlin {

    jvm {
        withJava()
        compilations.all {
            kotlinOptions {
                apiVersion = "1.9"
                languageVersion = "1.9"
                jvmTarget = "15"
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
                api(kotlinxCoroutines)
                api(kotlinReflect)
                api(kotlinxCollectionsImmutable)

                api(korge)

                api(cobaltCore)
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

                api(snakeYaml)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlinTestJunit)

                implementation(mockitoCore)
                implementation(mockitoKotlin)
                implementation(assertjCore)
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
