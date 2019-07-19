package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetSourceType.JAR
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.tileset.transformer.toAWTColor
import java.awt.Font
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.io.File


class MonospaceAwtFontTileset(private val resource: TilesetResource)
    : Tileset<Graphics2D> {

    override val id: Identifier = IdentifierFactory.randomIdentifier()
    override val targetType = Graphics2D::class
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height

    private val font: Font

    init {
        require(resource.tileType == CHARACTER_TILE) {
            "Can't use a ${resource.tileType.name}-based TilesetResource for" +
                    " a CharacterTile-based tileset."
        }
        font = Font.createFont(
                Font.TRUETYPE_FONT,
                if (resource.tilesetSourceType == JAR) {
                    this::class.java.getResourceAsStream(resource.path)
                } else {
                    File(resource.path).inputStream()
                }).deriveFont(resource.height.toFloat())
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(font)
    }

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val s = tile.asCharacterTile().get().character.toString()

        val fm = surface.getFontMetrics(font)

        val x = position.x * width
        val y = position.y * height

        surface.font = font
        surface.color = tile.backgroundColor.toAWTColor()
        surface.fillRect(x, y, resource.width, resource.height)
        surface.color = tile.foregroundColor.toAWTColor()
        surface.drawString(s, x, y + fm.ascent)
    }
}
