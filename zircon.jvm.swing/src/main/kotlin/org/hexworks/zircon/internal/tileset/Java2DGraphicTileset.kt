package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.TileType.GRAPHIC_TILE
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.impl.GraphicTextureMetadata
import org.hexworks.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

@Suppress("unused")
class Java2DGraphicTileset(private val resource: TilesetResource)
    : Tileset<Graphics2D> {

    override val id: Identifier = resource.id
    override val targetType = Graphics2D::class
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height

    private val metadata: Map<String, GraphicTextureMetadata>
    private val source: BufferedImage

    init {
        require(resource.tileType == GRAPHIC_TILE) {
            "Can't use a ${resource.tileType.name}-based TilesetResource for" +
                    " a GraphicTile-based tileset."
        }

        val resourceStream: InputStream = ImageLoader.readImageStream(resource)

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
            tileData.name to GraphicTextureMetadata(
                    name = tileData.name,
                    tags = tileData.tags,
                    x = tileData.x,
                    y = tileData.y,
                    width = resource.width,
                    height = resource.height)
        }.toMap()
    }

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val texture = fetchTextureForTile(tile)
        val x = position.x * width
        val y = position.y * height
        surface.drawImage(texture.texture, x, y, null)
    }


    private fun fetchTextureForTile(tile: Tile): TileTexture<BufferedImage> {
        tile as? GraphicalTile ?: throw IllegalArgumentException("Wrong tile type")
        return metadata[tile.name]?.let { meta ->
            DefaultTileTexture(
                    width = width,
                    height = height,
                    texture = source.getSubimage(meta.x * width, meta.y * height, width, height))
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
