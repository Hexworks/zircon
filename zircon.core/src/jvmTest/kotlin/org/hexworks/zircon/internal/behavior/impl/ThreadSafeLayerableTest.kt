package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName")
class ThreadSafeLayerableTest {

    lateinit var target: ThreadSafeLayerable
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = TILESET
        target = ThreadSafeLayerable(SIZE)
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_added_Then_it_is_present() {
        val layer = buildLayer()

        target.addLayer(layer)

        assertThat(target.fetchLayerStates().toList()).containsExactly(layer.state)
        assertThat(target.layers).containsExactly(layer)
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_removed_Then_it_is_not_present() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)
        target.removeLayer(layer)

        assertThat(target.fetchLayerStates().toList()).isEmpty()
        assertThat(target.layers).isEmpty()
    }

    @Test
    fun Given_a_layerable_When_a_layer_is_removed_with_the_handle_Then_it_is_not_present() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer).removeLayer()

        assertThat(target.fetchLayerStates().toList()).isEmpty()
        assertThat(target.layers).isEmpty()
    }

    @Test
    fun Given_a_layerable_When_adding_and_changing_a_layer_Then_the_layerable_is_updated() {
        val layer = buildLayer()

        target.addLayer(layer)

        val tile = buildTile('x')

        layer.draw(tile, Position.defaultPosition())

        assertThat(layer.state.tiles).isEqualTo(mapOf(Position.defaultPosition() to tile))
        assertThat(target.fetchLayerStates().first()).isEqualTo(layer.state)
    }

    @Test
    fun Given_a_layerable_When_setting_and_changing_a_layer_Then_the_layerable_is_updated() {
        val layer = buildLayer()

        target.addLayer(buildLayer())
        target.setLayerAt(0, layer)

        val tile = buildTile('x')

        layer.draw(tile, Position.defaultPosition())

        val layerState = layer.state

        assertThat(layerState.tiles).isEqualTo(mapOf(Position.defaultPosition() to tile))
        assertThat(target.fetchLayerStates().first()).isEqualTo(layerState)
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

        assertThat(target.fetchLayerStates().toList()).containsExactly(newLayer.state)
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

        assertThat(target.fetchLayerStates().toList()).containsExactly(newLayer.state)
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

        assertThat(target.fetchLayerStates().toList()).containsExactly(newLayer.state)
    }

    private fun buildTile(char: Char): CharacterTile {
        return Tile.defaultTile()
                .withCharacter(char)
                .withBackgroundColor(RED)
                .withForegroundColor(GREEN)
    }

    private fun buildLayer(): InternalLayer {
        return LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build().asInternal()
    }

    companion object {
        val TILESET = CP437TilesetResources.wanderlust16x16()
        val SIZE = Size.create(80, 24)
    }
}
