package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.base.BaseCP437Tileset
import org.hexworks.zircon.internal.tileset.impl.CP437TextureMetadata
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.transformer.LibgdxTextureCloner
import org.hexworks.zircon.internal.tileset.transformer.LibgdxTextureColorizer
import org.hexworks.zircon.internal.util.Assets

/**
 * Represents a tileset which is backed by a sprite sheet.
 */
class LibgdxCP437Tileset(
    resource: TilesetResource,
    private val path: String
) : BaseCP437Tileset<SpriteBatch, TextureRegion>(
    resource = resource,
    targetType = SpriteBatch::class
) {

    private val texture: Texture by lazy {
        if (!Assets.MANAGER.isLoaded(path)) {
            Assets.MANAGER.load(Assets.getCP437TextureDescriptor(path))
        }
        while (!Assets.MANAGER.update()) {
            //Loading...
        }
        Assets.MANAGER.get(Assets.getCP437TextureDescriptor(path))
    }

    override fun drawTile(tile: Tile, surface: SpriteBatch, position: Position) {
        val x = position.x.toFloat()
        val y = position.y.toFloat()
        val tileSprite = Sprite(fetchTextureForTile(tile, position).texture)
        tileSprite.setOrigin(0f, 0f)
        tileSprite.setOriginBasedPosition(x, y)
        tileSprite.flip(false, true)
        tileSprite.color = Color(
            tile.foregroundColor.red.toFloat() / 255,
            tile.foregroundColor.green.toFloat() / 255,
            tile.foregroundColor.blue.toFloat() / 255,
            tile.foregroundColor.alpha.toFloat() / 255
        )
        tileSprite.draw(surface)
    }

    override fun loadTileTexture(
        tile: CharacterTile,
        position: Position,
        meta: CP437TextureMetadata
    ): TileTexture<TextureRegion> {
        val tr = TextureRegion(texture, meta.x * width, meta.y * height, width, height)
        var image: TileTexture<TextureRegion> = DefaultTileTexture(
            width = width,
            height = height,
            texture = tr,
            cacheKey = tile.cacheKey
        )
        TILE_INITIALIZERS.forEach {
            image = it.transform(image, tile)
        }
        /*fixedTile.modifiers.filterIsInstance<TextureTransformModifier>().forEach {
            image = TEXTURE_TRANSFORMER_LOOKUP[it::class]?.transform(image, fixedTile) ?: image
        }*/
        return image
    }

    companion object {

        private val TILE_INITIALIZERS: List<TextureTransformer<TextureRegion>> = listOf(
            LibgdxTextureCloner(),
            LibgdxTextureColorizer()
        )
    }
}
