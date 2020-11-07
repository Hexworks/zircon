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
import TestLibs.mockitoAll
import TestLibs.mockitoKotlin

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

                implementation(mockitoAll)
                implementation(mockitoKotlin)
                implementation(assertjCore)
                implementation(logbackClassic)
                implementation(logbackCore)
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
