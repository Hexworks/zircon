import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

plugins {
    id("org.jetbrains.dokka")
    id("org.sonarqube") version "3.3"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlinx")
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "Hexworks_zircon")
        property("sonar.organization", "hexworks")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

tasks {
    val docsDir = projectDir.resolve("docs")

    val renameModulesToIndex by registering {
        doLast {
            logger.lifecycle("---=== Renaming -modules.html to index.html ===---")
            docsDir.listFiles()
                ?.filter { it.isDirectory }
                ?.forEach { dir ->
                    dir.listFiles()
                        ?.find { file -> file.name == "-modules.html" }
                        ?.renameTo(File(dir, "index.html"))
                }
        }
    }

    val generateDocsIndexTask by registering {
        doLast {
            logger.lifecycle("---=== Generating index.html for docs ===---")

            val docsSubDirs = docsDir.listFiles()
                ?.filter { it.isDirectory }
                ?.joinToString("\n") { dir -> "<li><a href=\"${dir.name}\">${dir.name}</a></li>" }

            val html = """|<!doctype html><head><title>Choose a Version</title></head><body>
                |<h1>Pick a Version</h1><ul>
                |$docsSubDirs
                |</ul></body></html>""".trimMargin()

            docsDir.resolve("index.html").writeText(html)
        }
    }

    dokkaHtmlMultiModule {
        outputDirectory.set(docsDir.resolve(project.version.toString() + "-KOTLIN"))
        finalizedBy(renameModulesToIndex, generateDocsIndexTask)
    }

    register<DokkaMultiModuleTask>("dokkaHtmlAsJavaMultiModule") {
        addSubprojectChildTasks("dokkaHtmlAsJava")
        removeChildTask(":zircon.core:dokkaHtmlAsJava") // TODO: remove after https://github.com/Kotlin/dokka/issues/1663 is fixed
        outputDirectory.set(docsDir.resolve("${project.version}-JAVA"))
        finalizedBy(renameModulesToIndex, generateDocsIndexTask)
    }
}
