package org.hexworks.zircon.internal.data

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.imageTile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.data.tile.ImageTile
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.Test

class DefaultImageTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        target.cacheKey shouldBe "ImageTile(t=/cp_437_tilesets/wanderlust_16x16.png,n=NAME)"
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        val copy = target.createCopy()
        copy shouldBe target
        // Note: For data classes, copy() returns a structurally equal instance
    }

    @Test
    fun shouldProperlyReportTileType() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        target.tileType shouldBe TileType.IMAGE_TILE
    }

    @Test
    fun shouldHaveNoModifiers() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        target.modifiers shouldBe emptySet()
    }

    @Test
    fun shouldProperlyCreateCopyWithName() {
        val otherName = "baz"
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        target.withName(otherName) shouldBe imageTile {
            name = otherName
            tileset = TILESET
        }
    }

    @Test
    fun shouldProperlyCreateCopyWithTileset() {
        val otherTileset = BuiltInCP437TilesetResource.BISASAM_16X16
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        target.withTileset(otherTileset) shouldBe imageTile {
            name = NAME
            tileset = otherTileset
        }
    }

    @Test
    fun shouldReturnNewInstanceWithModifiers() {
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        val withMods = target.withModifiers(setOf(SimpleModifiers.Blink))
        withMods.modifiers shouldBe setOf(SimpleModifiers.Blink)
    }

    companion object {

        const val NAME = "NAME"
        val TILESET = CP437TilesetResources.wanderlust16x16()
    }
}
