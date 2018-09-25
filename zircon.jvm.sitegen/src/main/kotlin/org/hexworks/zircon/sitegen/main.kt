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
    File("$projRoot/src/main/resources/tileset/$tilesetFile").copyTo(File("$outRoot/tileset/tileset.png"))

    val htmlContent = createHTML().html {
        head {
            link(href = "css/reset.css", rel = "stylesheet", type = "text/css")
            link(href = "css/main.css", rel = "stylesheet", type = "text/css")
        }
        body {
            div("page") {
                span("tile") {
                    style = """
                        background: url('http://localhost:63342/zircon/zircon.jvm.sitegen/build/site/tileset/tileset.png') 16px 0;
                        filter: hue-rotate(180deg);
                    """.trimIndent()
                }
                span("tile") {
                    style = """
                        background: url('http://localhost:63342/zircon/zircon.jvm.sitegen/build/site/tileset/tileset.png') 16px 0;
                    """.trimIndent()
                }
                span("tile") {
                    style = """
                        background: url('http://localhost:63342/zircon/zircon.jvm.sitegen/build/site/tileset/tileset.png') 16px 0;
                    """.trimIndent()
                }
                span("tile") {
                    style = """
                        background: url('http://localhost:63342/zircon/zircon.jvm.sitegen/build/site/tileset/tileset.png') 16px 0;
                    """.trimIndent()
                }
            }
        }
    }.toString()

    File("$outRoot/index.html").writeText(htmlContent)
}

