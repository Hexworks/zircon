package org.codetome.zircon.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.graphics.layer.DefaultLayer
import org.codetome.zircon.terminal.Size
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

        val layer = DefaultLayer(Size.ONE, TextCharacter.DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addOverlay(layer)

        assertThat(target.fetchOverlayZIntersection(Position.TOP_LEFT_CORNER))
                .isNotEmpty

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenRemoved() {

        val layer = DefaultLayer(Size.ONE, TextCharacter.DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addOverlay(layer)
        target.removeLayer(layer)

        assertThat(target.fetchOverlayZIntersection(Position.TOP_LEFT_CORNER))
                .isEmpty()

    }

    @Test
    fun shouldNotContainLayerWhenLayerIsAddedThenPopped() {

        val layer = DefaultLayer(Size.ONE, TextCharacter.DEFAULT_CHARACTER, Position.TOP_LEFT_CORNER)

        target.addOverlay(layer)
        val result = target.popOverlay()

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

        target.addOverlay(offset1x1layer)
        target.addOverlay(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.OFFSET_1x1)


        assertThat(result).containsExactly(expectedChar)
    }

    @Test
    fun shouldContainAllLayersWhenTwoLayersAreAddedAndTheyIntersect() {
        val expectedChar = TextCharacterBuilder.newBuilder()
                .character('1')
                .build()

        val offset1x1layer = DefaultLayer(Size.ONE, expectedChar, Position.OFFSET_1x1)
        val offset2x2layer = DefaultLayer(Size.ONE, expectedChar, Position.OFFSET_1x1)

        target.addOverlay(offset1x1layer)
        target.addOverlay(offset2x2layer)

        val result = target.fetchOverlayZIntersection(Position.OFFSET_1x1)


        assertThat(result).containsExactly(expectedChar, expectedChar)
    }

    companion object {
        val SIZE = Size(10, 10)
    }
}