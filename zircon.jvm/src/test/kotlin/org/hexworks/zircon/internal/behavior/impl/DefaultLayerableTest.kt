package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultLayerableTest {

    lateinit var target: DefaultLayerable
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = DefaultLayerable(
                size = SIZE)
    }

    @Test
    fun shouldContainLayerWhenLayerIsAdded() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)

        assertThat(target.getLayers())
                .isNotEmpty

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenRemoved() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)
        target.removeLayer(layer)

        assertThat(target.getLayers())
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {
        val layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.topLeftCorner())
                .build()

        target.pushLayer(layer)
        val result = target.popLayer()

        assertThat(target.getLayers())
                .isEmpty()
        assertThat(result.get()).isSameAs(layer)

    }

    @Test
    fun shouldContainBottomLayerOnlyWhenTwoLayersAreAddedAndTopDoesNotIntersectCoordinate() {
        val expectedChar = TileBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)
        val offset2x2layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.create(2, 2))
                .build()
                .fill(TileBuilder.newBuilder()
                        .character('2')
                        .build())


        target.pushLayer(offset1x1layer)
        target.pushLayer(offset2x2layer)

        val result = target.getLayers()
                .flatMap { it.snapshot().toList() }
                .filter { it.first == Position.offset1x1() }


        assertThat(result.map { it.second }).containsExactly(expectedChar)
    }

    @Test
    fun shouldContainAllLayersWhenTwoLayersAreAddedAndTheyIntersect() {
        val expectedChar = TileBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        val offset2x2layer = LayerBuilder.newBuilder()
                .size(Size.one())
                .offset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        target.pushLayer(offset1x1layer)
        target.pushLayer(offset2x2layer)

        val result = target.getLayers()
                .flatMap { it.snapshot().toList() }
                .filter { it.first == Position.offset1x1() }


        assertThat(result.map { it.second }).containsExactly(expectedChar, expectedChar)
    }

    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = CP437TilesetResource.WANDERLUST_16X16
    }
}
