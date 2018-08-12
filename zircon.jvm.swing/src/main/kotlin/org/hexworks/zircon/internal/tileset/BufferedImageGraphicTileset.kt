package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.GraphicalTilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.impl.GraphicTileTextureMetadata
import org.hexworks.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

@Suppress("unused")
class BufferedImageGraphicTileset(private val resource: TilesetResource)
    : Tileset<BufferedImage> {

    override val id: Identifier = resource.id
    override val sourceType = BufferedImage::class

    private val metadata: Map<String, GraphicTileTextureMetadata>
    private val source: BufferedImage

    init {
        require(resource.tileType == GraphicTile::class) {
            "Can't use a ${resource.tileType.simpleName}-based TilesetResource for" +
                    " a GraphicTile-based tileset."
        }

        val resourceStream: InputStream = if (resource is GraphicalTilesetResource) {
            this::class.java.getResourceAsStream(resource.path)
        } else {
            File(resource.path).inputStream()
        }

        val files: List<File> = unZipIt(resourceStream, createTempDir())
        val tileInfoSource = files.first { it.name == "tileinfo.yml" }.bufferedReader().use {
            it.readText()
        }
        val yaml = Yaml(Constructor(TileInfo::class.java))
        val tileInfo: TileInfo = yaml.load(tileInfoSource) as TileInfo
        // TODO: multi-file support
        val imageData = tileInfo.files.first()
        source = ImageIO.read(files.first { it.name == imageData.name })
        metadata = imageData.tiles.mapIndexed { i, tileData: TileData ->
            val (name, _, char) = tileData
            val cleanName = name.toLowerCase().trim()
            tileData.tags = tileData.tags
                    .plus(cleanName.split(" ").map { it }.toSet())
            if (char == ' ') {
                tileData.char = cleanName.first()
            }
            tileData.x = i.rem(imageData.tilesPerRow)
            tileData.y = i.div(imageData.tilesPerRow)
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
