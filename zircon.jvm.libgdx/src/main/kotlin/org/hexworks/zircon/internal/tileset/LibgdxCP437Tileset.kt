package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.benmanes.caffeine.cache.Caffeine
import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.transformer.LibgdxTextureCloner
import org.hexworks.zircon.internal.tileset.transformer.LibgdxTextureColorizer
import org.hexworks.zircon.internal.util.Assets
import java.util.concurrent.TimeUnit

/**
 * Represents a tileset which is backed by a sprite sheet.
 */
class LibgdxCP437Tileset(override val width: Int,
                         override val height: Int,
                         private val path: String)
    : Tileset<SpriteBatch> {

    override val id: Identifier = Identifier.randomIdentifier()
    override val targetType = SpriteBatch::class

    private val lookup = CP437TileMetadataLoader(
            width = width,
            height = height)

    private val cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(5000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build<String, TileTexture<TextureRegion>>()

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
        val tileSprite = Sprite(fetchTextureForTile(tile).texture)
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

    private fun fetchTextureForTile(tile: Tile): TileTexture<TextureRegion> {
        var fixedTile = tile as? CharacterTile ?: throw IllegalArgumentException("Wrong tile type")
        fixedTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
            if (modifier.canTransform(fixedTile)) {
                fixedTile = modifier.transform(fixedTile)
            }
        }
        val key = fixedTile.cacheKey
        val meta = CP437_METADATA[fixedTile.character]!!
        val tr = TextureRegion(texture, meta.x * width, meta.y * height, width, height)
        val maybeRegion = cache.getIfPresent(key)
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            var image: TileTexture<TextureRegion> = DefaultTileTexture(
                    width = width,
                    height = height,
                    texture = tr)
            TILE_INITIALIZERS.forEach {
                image = it.transform(image, fixedTile)
            }
            /*fixedTile.modifiers.filterIsInstance<TextureTransformModifier>().forEach {
                image = TEXTURE_TRANSFORMER_LOOKUP[it::class]?.transform(image, fixedTile) ?: image
            }*/
            cache.put(key, image)
            image
        }

    }

    companion object {
        private val UNICODE_TO_CP437_LOOKUP = mapOf(Pair(0x263A, 1), Pair(0x263B, 2), Pair(0x2665, 3), Pair(0x2666, 4), Pair(0x2663, 5), Pair(0x2660, 6), Pair(0x2022, 7), Pair(0x25D8, 8), Pair(0x25CB, 9), Pair(0x25D9, 10), Pair(0x2642, 11), Pair(0x2640, 12), Pair(0x266A, 13), Pair(0x266B, 14), Pair(0x263C, 15), Pair(0x25BA, 16), Pair(0x25C4, 17), Pair(0x2195, 18), Pair(0x203C, 19), Pair(0xB6, 20), Pair(0xA7, 21), Pair(0x25AC, 22), Pair(0x21A8, 23), Pair(0x2191, 24), Pair(0x2193, 25), Pair(0x2192, 26), Pair(0x2190, 27), Pair(0x221F, 28), Pair(0x2194, 29), Pair(0x25B2, 30), Pair(0x25BC, 31), Pair(0x20, 32), Pair(0x21, 33), Pair(0x22, 34), Pair(0x23, 35), Pair(0x24, 36), Pair(0x25, 37), Pair(0x26, 38), Pair(0x27, 39), Pair(0x28, 40), Pair(0x29, 41), Pair(0x2A, 42), Pair(0x2B, 43), Pair(0x2C, 44), Pair(0x2D, 45), Pair(0x2E, 46), Pair(0x2F, 47), Pair(0x30, 48), Pair(0x31, 49), Pair(0x32, 50), Pair(0x33, 51), Pair(0x34, 52), Pair(0x35, 53), Pair(0x36, 54), Pair(0x37, 55), Pair(0x38, 56), Pair(0x39, 57), Pair(0x3A, 58), Pair(0x3B, 59), Pair(0x3C, 60), Pair(0x3D, 61), Pair(0x3E, 62), Pair(0x3F, 63), Pair(0x40, 64), Pair(0x41, 65), Pair(0x42, 66), Pair(0x43, 67), Pair(0x44, 68), Pair(0x45, 69), Pair(0x46, 70), Pair(0x47, 71), Pair(0x48, 72), Pair(0x49, 73), Pair(0x4A, 74), Pair(0x4B, 75), Pair(0x4C, 76), Pair(0x4D, 77), Pair(0x4E, 78), Pair(0x4F, 79), Pair(0x50, 80), Pair(0x51, 81), Pair(0x52, 82), Pair(0x53, 83), Pair(0x54, 84), Pair(0x55, 85), Pair(0x56, 86), Pair(0x57, 87), Pair(0x58, 88), Pair(0x59, 89), Pair(0x5A, 90), Pair(0x5B, 91), Pair(0x5C, 92), Pair(0x5D, 93), Pair(0x5E, 94), Pair(0x5F, 95), Pair(0x60, 96), Pair(0x61, 97), Pair(0x62, 98), Pair(0x63, 99), Pair(0x64, 100), Pair(0x65, 101), Pair(0x66, 102), Pair(0x67, 103), Pair(0x68, 104), Pair(0x69, 105), Pair(0x6A, 106), Pair(0x6B, 107), Pair(0x6C, 108), Pair(0x6D, 109), Pair(0x6E, 110), Pair(0x6F, 111), Pair(0x70, 112), Pair(0x71, 113), Pair(0x72, 114), Pair(0x73, 115), Pair(0x74, 116), Pair(0x75, 117), Pair(0x76, 118), Pair(0x77, 119), Pair(0x78, 120), Pair(0x79, 121), Pair(0x7A, 122), Pair(0x7B, 123), Pair(0x7C, 124), Pair(0x7D, 125), Pair(0x7E, 126), Pair(0x2302, 127), Pair(0xC7, 128), Pair(0xFC, 129), Pair(0xE9, 130), Pair(0xE2, 131), Pair(0xE4, 132), Pair(0xE0, 133), Pair(0xE5, 134), Pair(0xE7, 135), Pair(0xEA, 136), Pair(0xEB, 137), Pair(0xE8, 138), Pair(0xEF, 139), Pair(0xEE, 140), Pair(0xEC, 141), Pair(0xC4, 142), Pair(0xC5, 143), Pair(0xC9, 144), Pair(0xE6, 145), Pair(0xC6, 146), Pair(0xF4, 147), Pair(0xF6, 148), Pair(0xF2, 149), Pair(0xFB, 150), Pair(0xF9, 151), Pair(0xFF, 152), Pair(0xD6, 153), Pair(0xDC, 154), Pair(0xA2, 155), Pair(0xA3, 156), Pair(0xA5, 157), Pair(0x20A7, 158), Pair(0x192, 159), Pair(0xE1, 160), Pair(0xED, 161), Pair(0xF3, 162), Pair(0xFA, 163), Pair(0xF1, 164), Pair(0xD1, 165), Pair(0xAA, 166), Pair(0xBA, 167), Pair(0xBF, 168), Pair(0x2310, 169), Pair(0xAC, 170), Pair(0xBD, 171), Pair(0xBC, 172), Pair(0xA1, 173), Pair(0xAB, 174), Pair(0xBB, 175), Pair(0x2591, 176), Pair(0x2592, 177), Pair(0x2593, 178), Pair(0x2502, 179), Pair(0x2524, 180), Pair(0x2561, 181), Pair(0x2562, 182), Pair(0x2556, 183), Pair(0x2555, 184), Pair(0x2563, 185), Pair(0x2551, 186), Pair(0x2557, 187), Pair(0x255D, 188), Pair(0x255C, 189), Pair(0x255B, 190), Pair(0x2510, 191), Pair(0x2514, 192), Pair(0x2534, 193), Pair(0x252C, 194), Pair(0x251C, 195), Pair(0x2500, 196), Pair(0x253C, 197), Pair(0x255E, 198), Pair(0x255F, 199), Pair(0x255A, 200), Pair(0x2554, 201), Pair(0x2569, 202), Pair(0x2566, 203), Pair(0x2560, 204), Pair(0x2550, 205), Pair(0x256C, 206), Pair(0x2567, 207), Pair(0x2568, 208), Pair(0x2564, 209), Pair(0x2565, 210), Pair(0x2559, 211), Pair(0x2558, 212), Pair(0x2552, 213), Pair(0x2553, 214), Pair(0x256B, 215), Pair(0x256A, 216), Pair(0x2518, 217), Pair(0x250C, 218), Pair(0x2588, 219), Pair(0x2584, 220), Pair(0x258C, 221), Pair(0x2590, 222), Pair(0x2580, 223), Pair(0x3B1, 224), Pair(0xDF, 225), Pair(0x393, 226), Pair(0x3C0, 227), Pair(0x3A3, 228), Pair(0x3C3, 229), Pair(0xB5, 230), Pair(0x3C4, 231), Pair(0x3A6, 232), Pair(0x398, 233), Pair(0x3A9, 234), Pair(0x3B4, 235), Pair(0x221E, 236), Pair(0x3C6, 237), Pair(0x3B5, 238), Pair(0x2229, 239), Pair(0x2261, 240), Pair(0xB1, 241), Pair(0x2265, 242), Pair(0x2264, 243), Pair(0x2320, 244), Pair(0x2321, 245), Pair(0xF7, 246), Pair(0x2248, 247), Pair(0xB0, 248), Pair(0x2219, 249), Pair(0xB7, 250), Pair(0x221A, 251), Pair(0x207F, 252), Pair(0xB2, 253), Pair(0x25A0, 254), Pair(0xA0, 255))

        val CP437_METADATA = UNICODE_TO_CP437_LOOKUP.map { (char, index) ->
            val x = index.rem(16)
            val y = index.div(16)
            Pair(char.toChar(), TileTextureMetadata(
                    x = x,
                    y = y))
        }.toMap()

        private val TILE_INITIALIZERS = listOf(
                LibgdxTextureCloner(),
                LibgdxTextureColorizer()
        )
    }

    class TileTextureMetadata(val x: Int,
                              val y: Int)
}
