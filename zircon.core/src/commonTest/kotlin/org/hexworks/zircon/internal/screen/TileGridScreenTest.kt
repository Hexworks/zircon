package org.hexworks.zircon.internal.screen

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.cobalt.databinding.api.value.ValueValidationFailedException
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.application.appConfig
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.component.builder.base.withPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("TestFunctionName")
class TileGridScreenTest {

    lateinit var target: TileGridScreen
    lateinit var tileset: TilesetResource
    lateinit var grid: DefaultTileGrid

    @BeforeTest
    fun setUp() {
        tileset = FONT
        grid = DefaultTileGrid(
            appConfig {
                defaultTileset = tileset
                size = SIZE
            })
        grid.application = ApplicationStub()
        target = TileGridScreen(grid)
    }

    @Test
    fun shouldUseGridsTilesetWhenCreating() {
        target.tileset.id shouldBe grid.tileset.id
    }

    @Test
    fun shouldProperlyOverrideGridTilesetWhenHasOverrideFontAndDisplayIsCalled() {
        val expectedFont = BuiltInCP437TilesetResource.ANIKKI_16X16
        target.tileset = expectedFont
        target.display()
        target.tileset.id shouldBe expectedFont.id
        grid.tileset.id shouldBe expectedFont.id
    }

    @Test
    fun shouldThrowExceptionWhenTilesetSizeIsIncompatible() {
        shouldThrow<ValueValidationFailedException> {
            target.tileset = BuiltInCP437TilesetResource.BISASAM_20X20
        }
    }

    @Test
    fun shouldBeDrawnWhenCharacterSet() {
        target.draw(CHAR, Position.OFFSET_1X1)
        target.getTileAtOrNull(Position.OFFSET_1X1)!! shouldBe CHAR

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.draw(CHAR, Position.OFFSET_1X1)
        target.display()

        target.clear()

        target.getTileAtOrNull(Position.OFFSET_1X1) shouldNotBe CHAR
    }

    @Test
    fun When_a_layer_and_a_component_is_added_Then_renderables_should_be_returned_in_proper_order() {
        val layer = layer {
            withSize {
                width = 3
                height = 4
            }
        }.apply {
            draw(characterTile { character = 'x' }, Position.OFFSET_1X1)
        }.asInternal()

        val button = buildButton {
            +"y"
            withPosition {
                x = 2
                y = 3
            }
        }

        val surfaceLayer = layer {
            withSize {
                width = 5
                height = 6
            }
        }.apply {
            draw(characterTile { character = 'z' }, Position.create(2, 3))
        }.asInternal()

        target.draw(surfaceLayer)
        target.addLayer(layer)
        target.addComponent(button)

        target.display()

        val renderables: List<Renderable> = target.renderables.toList()

        renderables.joinToString() shouldBe listOf(target.root, button, surfaceLayer, layer).joinToString()
    }

    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResources.rogueYun16x16()
        val CHAR = characterTile { character = 'x' }
    }
}
