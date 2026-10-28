import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.hexworks.zircon"
version = "2.0.0"

kotlin {
    explicitApi()
    // Pure Kotlin: every target is just a compilation. Android consumers resolve the jvm
    // variant (Java 17 bytecode), so there is no androidTarget here; zircon.compose has one.
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    js {
        browser {
            testTask {
                useMocha()
            }
        }
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useMocha()
            }
        }
        nodejs()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    linuxArm64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(libs.cobalt.core)
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.assertions.core)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.reflect)
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

mavenPublishing {
    publishToMavenCentral()
    // CI passes the key as ORG_GRADLE_PROJECT_signingInMemoryKey; local builds stay unsigned.
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }

    coordinates(group.toString(), "zircon.core", version.toString())

    pom {
        name = "zircon.core"
        description = "Multiplatform text GUI and roguelike tile library for Kotlin"
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

// The public API (org.hexworks.zircon.api) must not mention types from the internal package.
// The BCV dump lists every public JVM signature, so it is the cheapest place to check.
val checkApiLeaks by tasks.registering {
    val dump = layout.projectDirectory.file("api/zircon.core.api")
    inputs.file(dump)
    doLast {
        var current: String? = null
        val leaks = mutableListOf<String>()
        dump.asFile.readLines().forEach { line ->
            if (line.isNotBlank() && !line.first().isWhitespace()) {
                current = Regex("org/hexworks/zircon/[\\w/$]+").find(line)?.value
            } else if (current?.startsWith("org/hexworks/zircon/api/") == true && "org/hexworks/zircon/internal/" in line) {
                leaks += "$current: ${line.trim()}"
            }
        }
        check(leaks.isEmpty()) { "Internal types leak into the public API:\n" + leaks.joinToString("\n") }
    }
}
// apiCheck proves the dump matches the code, so the leak check runs on a verified dump.
checkApiLeaks.configure { mustRunAfter(tasks.named("apiCheck")) }
tasks.named("check") { dependsOn(checkApiLeaks) }
