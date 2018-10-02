package org.hexworks.zircon.sitegen

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.hexworks.zircon.api.CP437TilesetResources
import java.io.File

fun main(args: Array<String>) {

    val projRoot = "zircon.jvm.sitegen"
    val outRoot = "zircon.jvm.sitegen/build/site"
    val outDir = File(outRoot)
    val tilesetFile = CP437TilesetResources.wanderlust16x16().fileName

    outDir.deleteRecursively()

    outDir.mkdirs()

    File("$projRoot/src/main/resources/css/").copyRecursively(File("$outRoot/css/"))
    File("$projRoot/src/main/resources/font/").copyRecursively(File("$outRoot/font/"))

    val htmlContent = createHTML().html {
        head {
            link(href = "css/reset.css", rel = "stylesheet", type = "text/css")
            link(href = "css/main.css", rel = "stylesheet", type = "text/css")
        }
        body {
            div("page") {
                span("tile") {
                    text(0x2554)
                }
            }
        }
    }.toString()

    File("$outRoot/index.html").writeText(htmlContent)
}

