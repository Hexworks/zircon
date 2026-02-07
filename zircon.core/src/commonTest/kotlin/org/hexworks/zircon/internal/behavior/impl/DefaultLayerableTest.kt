package org.hexworks.zircon.internal.behavior.impl

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.InternalLayer
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("TestFunctionName")
class DefaultLayerableTest {

    lateinit var target: DefaultLayerable
    lateinit var tileset: TilesetResource

    @BeforeTest
    fun setUp() {
        tileset = TILESET
        target = DefaultLayerable(SIZE)
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_added_Then_it_is_present() {
        val layer = buildLayer()

        target.addLayer(layer)

        target.layers shouldContainExactly listOf(layer)
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_removed_Then_it_is_not_present() {
        val layer = layer {
            size = Size.one()
        }

        target.addLayer(layer)
        target.removeLayer(layer)

        target.layers.shouldBeEmpty()
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_removed_with_the_handle_Then_it_is_not_present() {
        val layer = layer {
            size = Size.one()
        }

        target.addLayer(layer).removeLayer()

        target.layers.shouldBeEmpty()
    }

    @Test
    fun Given_a_layerable_When_adding_and_changing_a_layer_Then_the_layerable_is_updated() {
        val layer = buildLayer()

        target.addLayer(layer)

        val tile = buildTile('x')

        layer.draw(tile, Position.defaultPosition())

        layer.tiles shouldBe mapOf(Position.defaultPosition() to tile)
    }

    @Test
    fun Given_a_layerable_When_setting_and_changing_a_layer_Then_the_layerable_is_updated() {
        val layer = buildLayer()

        target.addLayer(buildLayer())
        target.setLayerAt(0, layer)

        val tile = buildTile('x')

        layer.draw(tile, Position.defaultPosition())

        layer.tiles.toMap() shouldBe mapOf(Position.defaultPosition() to tile)
    }

    @Test
    fun Given_a_layerable_When_overwriting_a_layer_Then_the_layerable_contains_the_proper_layers() {

        val oldLayer = buildLayer().apply {
            fill(buildTile('y'))
        }
        val newLayer = buildLayer().apply {
            fill(buildTile('x'))
        }

        target.addLayer(oldLayer)
        target.setLayerAt(0, newLayer)

        target.layers shouldContainExactly listOf(newLayer)
    }

    @Test
    fun Given_a_layerable_When_setting_a_layer_and_changing_the_old_layer_Then_the_layerable_is_not_updated() {

        val oldTile = buildTile('x')
        val newTile = buildTile('y')

        val oldLayer = buildLayer()
        val newLayer = buildLayer().apply {
            fill(newTile)
        }

        target.addLayer(oldLayer)
        target.setLayerAt(0, newLayer)

        oldLayer.draw(oldTile, Position.defaultPosition())

        target.layers shouldContainExactly listOf(newLayer)
    }

    @Test
    fun Given_a_layerable_When_removing_a_layer_and_adding_a_new_one_Then_changing_the_old_layer_doesnt_affect_the_layerable() {

        val oldLayer = buildLayer().apply {
            fill(buildTile('x'))
        }
        val newLayer = buildLayer().apply {
            fill(buildTile('y'))
        }

        target.addLayer(oldLayer).removeLayer()
        target.addLayer(newLayer)

        oldLayer.draw(buildTile('z'), Position.defaultPosition())

        target.layers shouldContainExactly listOf(newLayer)
    }

    @Test
    fun Given_a_layer_handle_When_moving_up_by_one_level_when_cant_Then_the_new_order_is_correct() {

        val layer0 = buildLayer()
        val layer1 = buildLayer()

        target.addLayer(layer0)
        target.addLayer(layer1)

        val newLayer = buildLayer()

        val handle = target.addLayer(newLayer)

        val result = handle.moveOneLevelUp()

        result shouldBe false
        target.layers shouldContainExactly listOf(layer0, layer1, newLayer)
    }

    @Test
    fun Given_a_layer_handle_When_moving_down_by_one_level_when_cant_Then_the_new_order_is_correct() {

        val layer0 = buildLayer('0')
        val layer1 = buildLayer('1')
        val layer2 = buildLayer('3')

        val handle = target.addLayer(layer0)
        target.addLayer(layer1)
        target.addLayer(layer2)

        val result = handle.moveOneLevelDown()

        result shouldBe false
        target.layers shouldContainExactly listOf(layer0, layer1, layer2)
    }

    @Test
    fun Given_a_layer_handle_When_moving_down_by_one_level_Then_the_new_order_is_correct() {

        val layer0 = buildLayer()
        val layer1 = buildLayer()

        target.addLayer(layer0)
        target.addLayer(layer1)

        val newLayer = buildLayer()

        val handle = target.addLayer(newLayer)

        val result = handle.moveOneLevelDown()

        result shouldBe true
        target.layers shouldContainExactly listOf(layer0, newLayer, layer1)
    }

    @Test
    fun Given_a_layer_handle_When_moving_up_by_two_levels_Then_the_new_order_is_correct() {

        val layer0 = buildLayer('0')
        val layer1 = buildLayer('1')
        val layer2 = buildLayer('2')

        val handle = target.addLayer(layer0)
        target.addLayer(layer1)
        target.addLayer(layer2)

        val result = handle.moveByLevel(2)

        result shouldBe true
        target.layers shouldContainExactly listOf(layer1, layer2, layer0)
    }

    @Test
    fun Given_a_layer_handle_When_moving_down_by_two_levels_Then_the_new_order_is_correct() {

        val layer0 = buildLayer('0')
        val layer1 = buildLayer('1')
        val layer2 = buildLayer('2')

        target.addLayer(layer0)
        target.addLayer(layer1)
        val handle = target.addLayer(layer2)

        val result = handle.moveByLevel(-2)

        result shouldBe true
        target.layers shouldContainExactly listOf(layer2, layer0, layer1)
    }

    @Test
    fun Given_a_layer_handle_When_moving_it_right_Then_the_state_is_updated() {

        val layer0 = buildLayer('0')

        val handle = target.addLayer(layer0)

        val pos = Position.create(3, 4)

        handle.moveBy(pos)

        handle.position shouldBe pos

        target.layers.first().position shouldBe pos
    }

    private fun buildTile(char: Char): CharacterTile {
        return Tile.defaultTile()
            .withCharacter(char)
            .withBackgroundColor(DefaultAnsiPalette[ANSIColor.RED])
            .withForegroundColor(DefaultAnsiPalette[ANSIColor.GREEN])
    }

    private fun buildLayer(char: Char = ' '): InternalLayer {
        return layer {
            size = Size.one()
        }.asInternal().apply {
            fill(Tile.defaultTile().withCharacter(char))
        }
    }

    companion object {
        val TILESET = CP437TilesetResources.wanderlust16x16()
        val SIZE = Size.create(80, 24)
    }
}
