package org.hexworks.zircon.internal.screen

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.value.ValueValidationFailedException
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class TileGridScreenTest {

    lateinit var target: TileGridScreen
    lateinit var tileset: TilesetResource
    lateinit var grid: DefaultTileGrid

    @Before
    fun setUp() {
        tileset = FONT
        grid = DefaultTileGrid(AppConfig.newBuilder()
            .withDefaultTileset(tileset)
            .withSize(SIZE)
            .build())
        grid.application = ApplicationStub()
        target = TileGridScreen(grid)
    }

    @Test
    fun shouldUseGridsTilesetWhenCreating() {
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
        assertThat(target.getTileAtOrNull(Position.offset1x1())!!)
            .isEqualTo(CHAR)

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.draw(CHAR, Position.offset1x1())
        target.display()

        target.clear()

        assertThat(target.getTileAtOrNull(Position.offset1x1()))
            .isNotEqualTo(CHAR)
    }

    @Test
    fun When_a_layer_and_a_component_is_added_Then_renderables_should_be_returned_in_proper_order() {
        val layer = LayerBuilder.newBuilder()
            .withSize(Size.create(3, 4))
            .build().apply {
                draw(Tile.defaultTile().withCharacter('x'), Position.offset1x1())
            }.asInternal()

        val button = Components.button().withText("y").withPosition(Position.create(2, 3)).build()

        val surfaceLayer = LayerBuilder.newBuilder()
            .withSize(Size.create(5, 6))
            .build().apply {
                draw(Tile.defaultTile().withCharacter('z'), Position.create(2, 3))
            }.asInternal()

        target.draw(surfaceLayer)
        target.addLayer(layer)
        target.addComponent(button)

        target.display()

        Thread.sleep(500)
        val renderables: List<Renderable> = target.renderables.toList()

        assertThat(renderables.joinToString())
            .isEqualTo(listOf(target.root, button, surfaceLayer, layer).joinToString())
    }

    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResources.rogueYun16x16()
        val CHAR = TileBuilder.newBuilder()
            .withCharacter('x')
            .build()
    }
}
