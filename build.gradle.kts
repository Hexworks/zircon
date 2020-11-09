@file:Suppress("UnstableApiUsage")

import java.lang.StringBuilder

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
    outputDirectory.set(projectDir.resolve("docs").resolve(project.version.toString()))
}

tasks.dokkaHtmlMultiModule {
    doLast {
        println("---=== Generating index.html for docs ===---")
        val docs = File("docs")
        val html = StringBuilder()
        html.append("<html><head><title>Choose a Version</title></head><body>")
        html.append("<h1>Pick a Version</h1><ul>")
        docs.listFiles()
                ?.filter { it.name.contains("index.html").not() }
                ?.filter { it.isDirectory }
                ?.forEach { dir ->
                    html.append("<li><a href=\"${dir.name}\">${dir.name}</a></li>")
                }
        html.append("</ul></body></html>")
        File("docs/index.html").apply {
            if (exists()) {
                delete()
            }
            writeText(html.toString())
        }
    }
}
