import Libs.caffeine
import Libs.cobaltCore
import Libs.kotlinReflect
import Libs.kotlinStdLibCommon
import Libs.kotlinxCollectionsImmutable
import Libs.kotlinxCoroutines
import Libs.kotlinxCoroutinesTest
import Libs.logbackClassic
import Libs.slf4jApi
import Libs.snakeYaml
import TestLibs.assertJCore
import TestLibs.kotlinTestAnnotationsCommon
import TestLibs.kotlinTestCommon
import TestLibs.logbackCore
import TestLibs.mockitoAll
import TestLibs.mockitoKotlin

plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
}

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinStdLibCommon)

                api(kotlinxCoroutines)
                api(kotlinxCollectionsImmutable)
                api(kotlinReflect)

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
                api(kotlin("stdlib-jdk8"))
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
                implementation(assertJCore)
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
