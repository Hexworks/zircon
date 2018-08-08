package org.codetome.zircon.internal.tileset

import org.codetome.zircon.api.data.GraphicTile
import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import org.codetome.zircon.internal.tileset.impl.GraphicTileTextureMetadata
import org.codetome.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@Suppress("unused")
class BufferedImageGraphicTileset(private val resource: TilesetResource<ImageTile>)
    : Tileset<ImageTile, BufferedImage> {

    override val id: Identifier = resource.id
    override val sourceType = BufferedImage::class

    private val metadata: Map<String, GraphicTileTextureMetadata>
    private val source: BufferedImage

    init {
        val files = unZipIt(File(resource.path).inputStream(), createTempDir())
        val tileInfoSource = files.first { it.name == "tileinfo.yml" }.bufferedReader().use {
            it.readText()
        }
        val yaml = Yaml(Constructor(TileInfo::class.java))
        val tileInfo: TileInfo = yaml.load(tileInfoSource) as TileInfo
        // TODO: multi-file support
        val file = tileInfo.files.first()
        source = ImageIO.read(File(file.name))
        metadata = file.tiles.mapIndexed { i, tileData: TileData ->
            val (name, _, char) = tileData
            val cleanName = name.toLowerCase().trim()
            tileData.tags = tileData.tags
                    .plus(cleanName.split(" ").map { it }.toSet())
            if (char == ' ') {
                tileData.char = cleanName.first()
            }
            tileData.x = i.rem(file.tilesPerRow)
            tileData.y = i.div(file.tilesPerRow)
            tileData.name to GraphicTileTextureMetadata(
                    name = tileData.name,
                    tags = tileData.tags,
                    x = tileData.x,
                    y = tileData.y,
                    width = resource.width,
                    height = resource.height)
        }.toMap()
    }

    override fun width(): Int {
        return resource.width
    }

    override fun height(): Int {
        return resource.height
    }

    override fun supportsTile(tile: Tile): Boolean {
        TODO()
    }

    override fun fetchTextureForTile(tile: Tile): TileTexture<BufferedImage> {
        tile as? GraphicTile ?: throw IllegalArgumentException("Wrong tile type")
        return metadata[tile.name]?.let { meta ->
            DefaultTileTexture(
                    width = width(),
                    height = height(),
                    texture = source.getSubimage(meta.x * width(), meta.y * height(), width(), height()))
        } ?: throw NoSuchElementException("No texture with name '${tile.name}'.")
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
