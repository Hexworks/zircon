package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.util.Assets
import org.w3c.dom.Text
import kotlin.math.abs


class LibgdxMonospaceFontTileset(private val resource: TilesetResource)
    : Tileset<SpriteBatch> {

    override val id: Identifier = IdentifierFactory.randomIdentifier()
    override val targetType = SpriteBatch::class
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height

    private val font: BitmapFont

    init {
        require(resource.tileType == CHARACTER_TILE) {
            "Can't use a ${resource.tileType.name}-based TilesetResource for" +
                    " a CharacterTile-based tileset."
        }

        val fontParams = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
            fontFileName = resource.path.substring(1)
            fontParameters.size = resource.height
        }
        Assets.MANAGER.load("font.ttf", BitmapFont::class.java, fontParams)

        while(!Assets.MANAGER.update()) {}

        font = Assets.MANAGER.get("font.ttf")
    }

    override fun drawTile(tile: Tile, surface: SpriteBatch, position: Position) {
        val x = position.x.toFloat()
        val y = position.y.toFloat()
        val tileTex = fetchTextureForTile(tile) as OffsetTileTexture
        val tileSprite = Sprite(tileTex.texture)
        tileSprite.setOrigin(0f, 0f)
        tileSprite.setOriginBasedPosition(x + tileTex.xOffset.toFloat(), y - tileTex.yOffset.toFloat() - tileSprite.height)
        tileSprite.color = Color(
                tile.foregroundColor.red.toFloat() / 255,
                tile.foregroundColor.green.toFloat() / 255,
                tile.foregroundColor.blue.toFloat() / 255,
                tile.foregroundColor.alpha.toFloat() / 255
        )
        tileSprite.draw(surface)
    }

    private fun fetchTextureForTile(tile: Tile): TileTexture<TextureRegion> {
        val glyph = font.data.getGlyph(tile.asCharacterTile().get().character)
        val page = font.getRegion(glyph.page)
        val tr = TextureRegion(page.texture, glyph.u, glyph.v, glyph.u2, glyph.v2)

        return OffsetTileTexture(
                width = width,
                height = height,
                xOffset = glyph.xoffset,
                yOffset = glyph.yoffset,
                texture = tr)
    }

    class OffsetTileTexture<T>(override val width: Int,
                                   override val height: Int,
                                   val xOffset: Int,
                                   val yOffset: Int,
                                   override val texture: T) : TileTexture<T>
}
