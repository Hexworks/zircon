package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.font.impl.Java2DFont
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor


/**
 * This enum encapsulates the means of loading graphic tilesets.
 * You can either use a built-in tileset or you can load your own using [GraphicTilesetResource.loadGraphicTileset]
 */
enum class GraphicTilesetResource(private val tilesetName: String,
                                  val size: Int,
                                  private val fileName: String = "tileinfo.yml",
                                  val path: String = "/graphic_tilesets/${tilesetName}_${size}x$size/$fileName") {

    NETHACK_16X16("nethack", 16);


    companion object {

        /**
         * Loads a tileset from the given `source` as a [Java2DFont].
         * *Note that* it is your responsibility to supply the proper parameters for
         * this method!
         */
        fun loadGraphicTileset() {
            val tileinfo = GraphicTilesetResource::class.java.getResourceAsStream(NETHACK_16X16.path).bufferedReader().use {
                it.readText()
            }
            val yaml = Yaml(Constructor(TileInfo::class.java))
            val result = yaml.load(tileinfo)
            println(result)
        }
    }
}

data class TileInfo(var name: String,
                    var size: Int,
                    var tilesPerRow: Int,
                    var files: List<TileFile>) {
    constructor() : this(name = "",
            size = 0,
            tilesPerRow = 0,
            files = listOf())
}

data class TileFile(var name: String,
                    var tiles: List<TileData>) {
    constructor() : this(name = "", tiles = listOf())
}

data class TileData(var name: String,
                    var tags: List<String> = listOf(),
                    var char: Char = ' ',
                    var description: String = "") {
    constructor(): this(name = "")

}