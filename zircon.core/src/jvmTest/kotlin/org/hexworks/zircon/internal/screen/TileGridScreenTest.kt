package org.hexworks.zircon.internal.screen

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.value.ValueValidationFailedException

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class TileGridScreenTest {

    lateinit var target: TileGridScreen
    lateinit var tileset: TilesetResource
    lateinit var grid: ThreadSafeTileGrid

    @Before
    fun setUp() {
        AppConfig.newBuilder().enableBetaFeatures().build()
        tileset = FONT
        grid = ThreadSafeTileGrid(
                initialTileset = tileset,
                initialSize = SIZE)
        MockitoAnnotations.initMocks(this)
        target = TileGridScreen(grid)
    }

    @Test
    fun shouldUseTerminalsFontWhenCreating() {
        assertThat(target.tileset.id)
                .isEqualTo(grid.tileset.id)
    }

    @Test
    fun shouldProperlyOverrideGridTilesetWhenHasOverrideFontAndDisplayIsCalled() {
        val expectedFont = BuiltInCP437TilesetResource.ANIKKI_16X16
        target.tileset = expectedFont
        target.display()
        assertThat(target.tileset.id).isEqualTo(expectedFont.id)
        assertThat(grid.tileset.id).isEqualTo(expectedFont.id)
    }

    @Test(expected = ValueValidationFailedException::class)
    fun shouldProperlyThrowExceptionWhenTyringToSetNonCompatibleFont() {
        target.tileset = BuiltInCP437TilesetResource.BISASAM_20X20
    }

    @Test
    fun shouldBeDrawnWhenCharacterSet() {
        target.draw(CHAR, Position.offset1x1())
        assertThat(target.getTileAt(Position.offset1x1()).get())
                .isEqualTo(CHAR)

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.draw(CHAR, Position.offset1x1())
        target.display()

        target.clear()

        assertThat(target.getTileAt(Position.offset1x1()))
                .isNotEqualTo(CHAR)
    }

    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResources.rogueYun16x16()
        val CHAR = TileBuilder.newBuilder()
                .withCharacter('x')
                .build()
    }
}
