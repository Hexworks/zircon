package org.codetome.zircon.graphics.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Symbols
import org.codetome.zircon.Position
import org.codetome.zircon.Position.Companion.DEFAULT_POSITION
import org.codetome.zircon.Position.Companion.OFFSET_1x1
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.graphics.shape.DefaultShapeRenderer
import org.codetome.zircon.terminal.Size
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultShapeRendererTest {

    lateinit var target: DefaultShapeRenderer

    private val modifiedPoints = mutableListOf<Position>()

    private val textImage: TextImage = TextImageBuilder.newBuilder()
            .size(Size(50, 50))
            .filler(TextCharacter.DEFAULT_CHARACTER)
            .build()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultShapeRenderer()
        target.setCallback(object : DefaultShapeRenderer.Callback {
            override fun onPoint(position: Position, character: TextCharacter) {
                modifiedPoints.add(position)
                textImage.setCharacterAt(position, character)
            }
        })
    }

    @Test
    fun shouldProperlyDrawStraightHorizontalLine() {
        target.drawLine(
                fromPoint = Position(0, 0),
                toPoint = Position(2, 0),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                DEFAULT_POSITION,
                DEFAULT_POSITION.withRelativeColumn(1),
                DEFAULT_POSITION.withRelativeColumn(2))
    }

    @Test
    fun shouldProperlyDrawStraightVerticalLine() {
        target.drawLine(
                fromPoint = Position(0, 0),
                toPoint = Position(0, 2),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                DEFAULT_POSITION,
                DEFAULT_POSITION.withRelativeRow(1),
                DEFAULT_POSITION.withRelativeRow(2))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        target.drawLine(
                fromPoint = Position(0, 0),
                toPoint = Position(5, 4),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column = 0, row = 0),
                Position(column = 1, row = 1),
                Position(column = 2, row = 2),
                Position(column = 3, row = 2),
                Position(column = 4, row = 3),
                Position(column = 5, row = 4))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        target.drawLine(
                fromPoint = Position(0, 0),
                toPoint = Position(4, 5),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column = 0, row = 0),
                Position(column = 1, row = 1),
                Position(column = 2, row = 2),
                Position(column = 2, row = 3),
                Position(column = 3, row = 4),
                Position(column = 4, row = 5))
    }

    @Test
    fun shouldProperlyDrawTriangle() {
        target.drawTriangle(
                p1 = OFFSET_1x1,
                p2 = Position(4, 1),
                p3 = Position(4, 4),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column=1, row=1),
                Position(column=2, row=1),
                Position(column=3, row=1),
                Position(column=4, row=1),
                Position(column=4, row=2),
                Position(column=4, row=3),
                Position(column=4, row=4),
                Position(column=2, row=2),
                Position(column=3, row=3))
    }

    @Test
    fun shouldProperlyDrawRectangle() {
        target.drawRectangle(
                topLeft = OFFSET_1x1,
                size = Size(3, 3),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column=1, row=1),
                Position(column=2, row=1),
                Position(column=3, row=1),
                Position(column=3, row=2),
                Position(column=3, row=3),
                Position(column=2, row=3),
                Position(column=1, row=3),
                Position(column=1, row=2))
    }

    @Test
    fun shouldProperlyFillRectangle() {
        target.fillRectangle(
                topLeft = OFFSET_1x1,
                size = Size(3, 3),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column=1, row=1),
                Position(column=2, row=1),
                Position(column=3, row=1),
                Position(column=1, row=2),
                Position(column=2, row=2),
                Position(column=3, row=2),
                Position(column=1, row=3),
                Position(column=2, row=3),
                Position(column=3, row=3))
    }

    @Ignore
    @Test
    fun shouldProperlyFillTriangle() {
        // TODO: fix algo!
        target.fillTriangle(
                p1 = OFFSET_1x1,
                p2 = Position(5, 1),
                p3 = Position(5, 5),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column = 0, row = 0),
                Position(column = 3, row = 0),
                Position(column = 2, row = 0),
                Position(column = 1, row = 0),
                Position(column = 0, row = 0),
                Position(column = 2, row = 1),
                Position(column = 1, row = 1),
                Position(column = 0, row = 1),
                Position(column = 1, row = 2),
                Position(column = 0, row = 2),
                Position(column = 0, row = 3))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        // TODO: fix triangle fill algo
        target.fillTriangle(
                p1 = DEFAULT_POSITION,
                p2 = Position(3, 1),
                p3 = Position(2, 5),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                Position(column = 0, row = 0),
                Position(column = 0, row = 1),
                Position(column = 1, row = 1),
                Position(column = 2, row = 1),
                Position(column = 3, row = 1),
                Position(column = 0, row = 2),
                Position(column = 1, row = 2),
                Position(column = 2, row = 2),
                Position(column = 3, row = 2),
                Position(column = 1, row = 3),
                Position(column = 2, row = 3),
                Position(column = 1, row = 4),
                Position(column = 2, row = 4),
                Position(column = 2, row = 5))
    }

    companion object {
        val CHAR = TextCharacterBuilder.newBuilder()
                .character(Symbols.BLOCK_MIDDLE)
                .build()
    }

}