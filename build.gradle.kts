plugins {
    id("org.jetbrains.dokka") version "1.4.10.2"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        kotlinx()
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}