package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.imageTile
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.TileType
import org.junit.Test

class DefaultImageTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.cacheKey).isEqualTo("ImageTile(t=/cp_437_tilesets/wanderlust_16x16.png,n=NAME)")
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.createCopy())
            .isEqualTo(target)
            .isNotSameAs(target)
    }

    @Test
    fun shouldProperlyReportTileType() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.tileType).isEqualTo(TileType.IMAGE_TILE)
    }

    @Test
    fun shouldHaveTransparentFGColor() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.foregroundColor).isEqualTo(TileColor.transparent())
    }

    @Test
    fun shouldHaveTransparentBGColor() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.backgroundColor).isEqualTo(TileColor.transparent())
    }

    @Test
    fun shouldHaveNoModifiers() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.modifiers).isEmpty()
    }

    @Test
    fun shouldHaveTheEmptyStyleSet() {
        val target: ImageTile = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.styleSet).isSameAs(StyleSet.empty())
    }

    @Test
    fun shouldProperlyCreateCopyWithName() {
        val otherName = "baz"
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.withName(otherName))
            .isEqualTo(imageTile {
                name = otherName
                tileset = TILESET
            })
    }

    @Test
    fun shouldProperlyCreateCopyWithTileset() {
        val otherTileset = BuiltInCP437TilesetResource.BISASAM_16X16
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.withTileset(otherTileset))
            .isEqualTo(imageTile {
                name = NAME
                tileset = otherTileset
            })
    }

    @Test
    fun shouldReturnItselfForAllStyleCopiers() {
        val target = imageTile {
            name = NAME
            tileset = TILESET
        }

        assertThat(target.withForegroundColor(ANSITileColor.YELLOW)).isSameAs(target)
        assertThat(target.withBackgroundColor(ANSITileColor.GREEN)).isSameAs(target)
        assertThat(target.withStyle(StyleSet.defaultStyle())).isSameAs(target)
        assertThat(target.withModifiers(setOf(SimpleModifiers.Blink))).isSameAs(target)
        assertThat(target.withAddedModifiers(SimpleModifiers.Blink)).isSameAs(target)
        assertThat(target.withRemovedModifiers(SimpleModifiers.Blink)).isSameAs(target)
        assertThat(target.withRemovedModifiers(setOf(SimpleModifiers.Blink))).isSameAs(target)
        assertThat(target.withNoModifiers()).isSameAs(target)
    }

    companion object {

        const val NAME = "NAME"
        val TILESET = CP437TilesetResources.wanderlust16x16()
    }
}
