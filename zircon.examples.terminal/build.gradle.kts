import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// The demos in a terminal. `ZIRCON_EXAMPLES_FILES` points at the demo data files; the run
// tasks set it, a packaged binary needs it set by hand.
val examplesFiles = layout.projectDirectory.dir("../zircon.examples/resources/files").asFile.absolutePath

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
        mainRun {
            mainClass = "MainKt"
        }
    }
    listOf(linuxX64(), linuxArm64(), macosX64(), macosArm64()).forEach { target ->
        target.binaries.executable {
            entryPoint = "main"
            runTask?.environment("ZIRCON_EXAMPLES_FILES", examplesFiles)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":zircon.examples"))
            implementation(project(":zircon.terminal"))
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

tasks.withType<JavaExec>().configureEach {
    environment("ZIRCON_EXAMPLES_FILES", examplesFiles)
    standardInput = System.`in`
}

// Writes the JVM runtime classpath to build/jvm-classpath.txt so the launcher can be run
// outside Gradle (Gradle does not hand a terminal to the process it runs).
tasks.register("jvmClasspath") {
    val jarFile = tasks.named<Jar>("jvmJar").flatMap { it.archiveFile }
    val runtime = configurations.named("jvmRuntimeClasspath")
    val classpath = runtime.map { it.asPath }
    inputs.file(jarFile)
    inputs.files(runtime)
    val out = layout.buildDirectory.file("jvm-classpath.txt")
    outputs.file(out)
    doLast {
        out.get().asFile.writeText(jarFile.get().asFile.path + File.pathSeparator + classpath.get())
    }
}
