package org.codetome.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.internal.graphics.DefaultLayer
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder.Companion.DEFAULT_CHARACTER
import org.junit.Before
import org.junit.Test

class DefaultLayerableTest {

    lateinit var target: DefaultLayerable

    @Before
    fun setUp() {
        target = DefaultLayerable(SIZE)
    }

    @Test
    fun shouldContainLayerWhenLayerIsAdded() {

        val layer = DefaultLayer(Size.ONE, DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addLayer(layer)

        assertThat(target.fetchOverlayZIntersection(Position.TOP_LEFT_CORNER))
                .isNotEmpty

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenRemoved() {

        val layer = DefaultLayer(Size.ONE, DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addLayer(layer)
        target.removeLayer(layer)

        assertThat(target.fetchOverlayZIntersection(Position.TOP_LEFT_CORNER))
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {

        val layer = DefaultLayer(Size.ONE, DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addLayer(layer)
        val result = target.popLayer()

        assertThat(target.fetchOverlayZIntersection(Position.TOP_LEFT_CORNER))
                .isEmpty()
        assertThat(result.get()).isSameAs(layer)

    }

    @Test
    fun shouldContainBottomLayerOnlyWhenTwoLayersAreAddedAndTopDoesNotIntersectCoordinate() {
        val expectedChar = TextCharacterBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = DefaultLayer(Size.ONE, expectedChar, Position.OFFSET_1x1)
        val offset2x2layer = DefaultLayer(Size.ONE, TextCharacterBuilder.newBuilder()
                .character('2')
                .build(), Position(2, 2))

        target.addLayer(offset1x1layer)
        target.addLayer(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.OFFSET_1x1)


        assertThat(result).containsExactly(expectedChar)
    }

    @Test
    fun shouldProperlyMarkDrainedLayerPositionsDirtyWhenLayersAreDrained() {
        val dirty0 = Position.of(1, 2)
        val dirty1 = Position.of(3, 4)

        target.addLayer(LayerBuilder.newBuilder()
                .offset(dirty0)
                .build())

        target.addLayer(LayerBuilder.newBuilder()
                .offset(dirty1)
                .build())

        target.drainLayers()
        assertThat(target.drainDirtyPositions())
                .containsExactlyInAnyOrder(dirty0, dirty1)

    }

    @Test
    fun shouldContainAllLayersWhenTwoLayersAreAddedAndTheyIntersect() {
        val expectedChar = TextCharacterBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = DefaultLayer(Size.ONE, expectedChar, Position.OFFSET_1x1)
        val offset2x2layer = DefaultLayer(Size.ONE, expectedChar, Position.OFFSET_1x1)

        target.addLayer(offset1x1layer)
        target.addLayer(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.OFFSET_1x1)


        assertThat(result).containsExactly(expectedChar, expectedChar)
    }

    companion object {
        val SIZE = Size(10, 10)
    }
}