import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.hexworks.zircon"
version = "2.0.0"

kotlin {
    jvm {}

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.core"))

            api(libs.kotlin.reflect)
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlin.logging)
            api(libs.kotlinx.coroutines.core)

            api(libs.cobalt.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }

        jvmMain.dependencies {
            api(libs.slf4j)
            api(libs.logback)
            api(libs.kotlinx.coroutines.swing)
            implementation(compose.desktop.currentOs)
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

    coordinates(group.toString(), "zircon.compose", version.toString())

    pom {
        name = "zircon.compose"
        description = "Compose Multiplatform renderer implementation for Zircon"
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
