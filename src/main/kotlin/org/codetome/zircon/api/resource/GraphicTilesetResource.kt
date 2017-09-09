package org.codetome.zircon.api.resource

import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.cache.DefaultFontRegionCache
import org.codetome.zircon.internal.font.cache.GraphicTilesetCachingStrategy
import org.codetome.zircon.internal.font.impl.Java2DFont
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionCloner
import org.codetome.zircon.internal.font.transformer.Java2DFontRegionColorizer
import org.codetome.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.awt.image.BufferedImage
import javax.imageio.ImageIO


/**
 * This enum encapsulates the means of loading graphic tilesets.
 * You can either use a built-in tileset or you can load your own using [GraphicTilesetResource.loadGraphicTileset]
 */
enum class GraphicTilesetResource(private val tilesetName: String,
                                  val size: Int,
                                  val path: String = "/graphic_tilesets/${tilesetName}_${size}x$size.zip") {

    NETHACK_16X16("nethack", 16);

    /**
     * Loads this built-in tileset as a [Java2DFont].
     */
    fun toFont(metadataPickingStrategy: MetadataPickingStrategy) =
            loadGraphicTileset(this.javaClass.getResource(path).path, metadataPickingStrategy)


    companion object {

        private val GRAPHIC_TILESET_TRANSFORMERS = listOf(
                Java2DFontRegionCloner(),
                Java2DFontRegionColorizer())

        /**
         * Loads a tileset from the given `sourceZip` as a [Java2DFont].
         * *Note that* it is your responsibility to supply the proper parameters for
         * this method!
         */
        fun loadGraphicTileset(sourceZipPath: String,
                               metadataPickingStrategy: MetadataPickingStrategy): Font<BufferedImage> {
            val files = unZipIt(sourceZipPath, createTempDir())
            val tileInfoSource = files.filter { it.name == "tileinfo.yml" }.first().bufferedReader().use {
                it.readText()
            }
            val yaml = Yaml(Constructor(TileInfo::class.java))
            val tileInfo: TileInfo = yaml.load(tileInfoSource) as TileInfo
            // TODO: multi-file support
            val file = tileInfo.files.first()
            val metadata = file.tiles.mapIndexed { i, tileData: TileData ->
                val (name, _, char) = tileData
                val cleanName = name.toLowerCase().trim()
                tileData.tags = tileData.tags
                        .plus(cleanName.split(" ").map { it }.toSet())
                if (char == ' ') {
                    tileData.char = cleanName.first()
                }
                tileData.x = i.rem(file.tilesPerRow)
                tileData.y = i.div(file.tilesPerRow)
                CharacterMetadata(
                        char = tileData.char,
                        tags = tileData.tags,
                        x = tileData.x,
                        y = tileData.y)
            }.groupBy { it.char }
            return Java2DFont(
                    source = ImageIO.read(files.first { it.name == file.name }),
                    metadata = metadata,
                    width = tileInfo.size,
                    height = tileInfo.size,
                    cache = DefaultFontRegionCache(GraphicTilesetCachingStrategy()),
                    regionTransformers = GRAPHIC_TILESET_TRANSFORMERS,
                    metadataPickingStrategy = metadataPickingStrategy)
        }
    }

    data class TileInfo(var name: String,
                        var size: Int,
                        var files: List<TileFile>) {
        constructor() : this(name = "",
                size = 0,
                files = listOf())
    }

    data class TileFile(var name: String,
                        var tilesPerRow: Int,
                        var tiles: List<TileData>) {
        constructor() : this(name = "",
                tilesPerRow = 0,
                tiles = listOf())
    }

    data class TileData(var name: String,
                        var tags: Set<String> = setOf(),
                        var char: Char = ' ',
                        var description: String = "",
                        var x: Int = -1,
                        var y: Int = -1) {
        constructor() : this(name = "")

    }
}