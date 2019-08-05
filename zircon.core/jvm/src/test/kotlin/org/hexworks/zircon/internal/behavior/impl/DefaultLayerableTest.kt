package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultLayerableTest {

    lateinit var target: DefaultLayerable
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = DefaultLayerable()
    }

    @Test
    fun shouldContainLayerWhenLayerIsAdded() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)

        assertThat(target.layers)
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

        assertThat(target.layers)
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {
        val layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.zero())
                .build()

        target.addLayer(layer)
        val result = target.popLayer()

        assertThat(target.layers)
                .isEmpty()
        assertThat(result.get()).isSameAs(layer)

    }

    @Test
    fun shouldContainBottomLayerOnlyWhenTwoLayersAreAddedAndTopDoesNotIntersectCoordinate() {
        val expectedChar = TileBuilder.newBuilder()
                .withCharacter('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.offset1x1())
                .build()
                .fill(expectedChar)
        val offset2x2layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.create(2, 2))
                .build()
                .fill(TileBuilder.newBuilder()
                        .withCharacter('2')
                        .build())


        target.addLayer(offset1x1layer)
        target.addLayer(offset2x2layer)

        val result = target.layers
                .flatMap { it.createSnapshot().cells }
                .filter { it.position == Position.offset1x1() }


        assertThat(result.map { it.tile }).containsExactly(expectedChar)
    }

    @Test
    fun shouldContainAllLayersWhenTwoLayersAreAddedAndTheyIntersect() {
        val expectedChar = TileBuilder.newBuilder()
                .withCharacter('1')
                .build()

        val offset1x1layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        val offset2x2layer = LayerBuilder.newBuilder()
                .withSize(Size.one())
                .withOffset(Position.offset1x1())
                .build()
                .fill(expectedChar)

        target.addLayer(offset1x1layer)
        target.addLayer(offset2x2layer)

        val result = target.layers
                .flatMap { it.createSnapshot().cells }
                .filter { it.position == Position.offset1x1() }


        assertThat(result.map { it.tile }).containsExactly(expectedChar, expectedChar)
    }

    companion object {
        val FONT = CP437TilesetResources.wanderlust16x16()
    }
}
