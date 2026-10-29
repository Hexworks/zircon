import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.hexworks.zircon"
version = "2.0.0"

kotlin {
    explicitApi()
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    // The tty layer is POSIX (termios, ioctl, poll); mingwX64 needs the Win32 console API
    // and is not a target yet.
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":zircon.core"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions.core)
        }
    }
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

    coordinates(group.toString(), "zircon.terminal", version.toString())

    pom {
        name = "zircon.terminal"
        description = "Terminal (ANSI) renderer for Zircon"
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
