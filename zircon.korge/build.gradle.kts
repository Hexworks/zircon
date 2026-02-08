import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.hexworks.zircon"
version = "2.0.0"

kotlin {
    jvm {}
    js(IR) {
        browser {
            testTask {
                useMocha()
            }
        }
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.core"))

            api(libs.kotlin.reflect)
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlin.logging)

            api(libs.cobalt.core)
            api(libs.korge.all)
        }

        jvmMain.dependencies {
            api(libs.slf4j)
            api(libs.logback)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions.core)
        }

        jvmTest.dependencies {
            implementation(libs.mockk)
        }
    }
}
mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "zircon.korge", version.toString())

    pom {
        name = "ziron.korge"
        description = "KorGE renderer implementation for Zircon"
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
