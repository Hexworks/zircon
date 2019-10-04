package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class ThreadSafeLayerableTest {

    lateinit var target: ThreadSafeLayerable
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = TILESET
        target = ThreadSafeLayerable(SIZE)
    }

    @Test
    fun shouldContainLayerWhenLayerIsAdded() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)

        assertThat(target.layerStates)
                .isNotEmpty

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenRemoved() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)
        target.removeLayer(layer)

        assertThat(target.layerStates)
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)
        target.removeLayer(layer)

        assertThat(target.layerStates)
                .isEmpty()
    }

    companion object {
        val TILESET = CP437TilesetResources.wanderlust16x16()
        val SIZE = Sizes.create(80, 24)
    }
}
