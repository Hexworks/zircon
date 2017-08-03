package org.codetome.zircon.graphics.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TerminalPosition.Companion.DEFAULT_POSITION
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextCharacterBuilder
import org.codetome.zircon.terminal.TerminalSize
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultShapeRendererTest {

    lateinit var target: DefaultShapeRenderer

    private val modifiedPoints = mutableListOf<TerminalPosition>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultShapeRenderer()
        target.setCallback(object : DefaultShapeRenderer.Callback {
            override fun onPoint(column: Int, row: Int, character: TextCharacter) {
                modifiedPoints.add(TerminalPosition(column, row))
            }
        })
    }

    @Test
    fun shouldProperlyDrawStraightLineThroughColumns() {
        target.drawLine(
                fromPoint = TerminalPosition(0, 0),
                toPoint = TerminalPosition(2, 0),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                DEFAULT_POSITION,
                DEFAULT_POSITION.withRelativeColumn(1),
                DEFAULT_POSITION.withRelativeColumn(2))
    }

    @Test
    fun shouldProperlyDrawStraightLineThroughRows() {
        target.drawLine(
                fromPoint = TerminalPosition(0, 0),
                toPoint = TerminalPosition(0, 2),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                DEFAULT_POSITION,
                DEFAULT_POSITION.withRelativeRow(1),
                DEFAULT_POSITION.withRelativeRow(2))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToX() {
        target.drawLine(
                fromPoint = TerminalPosition(0, 0),
                toPoint = TerminalPosition(5, 4),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 1, row = 1),
                TerminalPosition(column = 2, row = 2),
                TerminalPosition(column = 3, row = 2),
                TerminalPosition(column = 4, row = 3),
                TerminalPosition(column = 5, row = 4))
    }

    @Test
    fun shouldProperlyDrawIrregularLineDeviatingToY() {
        target.drawLine(
                fromPoint = TerminalPosition(0, 0),
                toPoint = TerminalPosition(4, 5),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 1, row = 1),
                TerminalPosition(column = 2, row = 2),
                TerminalPosition(column = 2, row = 3),
                TerminalPosition(column = 3, row = 4),
                TerminalPosition(column = 4, row = 5))
    }

    @Test
    fun shouldProperlyDrawTriangle() {
        target.drawTriangle(
                p1 = DEFAULT_POSITION,
                p2 = TerminalPosition(3, 0),
                p3 = TerminalPosition(0, 3),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 1, row = 0),
                TerminalPosition(column = 2, row = 0),
                TerminalPosition(column = 3, row = 0),
                TerminalPosition(column = 3, row = 0),
                TerminalPosition(column = 2, row = 1),
                TerminalPosition(column = 1, row = 2),
                TerminalPosition(column = 0, row = 3),
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 0, row = 1),
                TerminalPosition(column = 0, row = 2),
                TerminalPosition(column = 0, row = 3))
    }

    @Test
    fun shouldProperlyDrawRectangle() {
        target.drawRectangle(
                topLeft = DEFAULT_POSITION,
                size = TerminalSize(3, 3),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 1, row = 0),
                TerminalPosition(column = 2, row = 0),
                TerminalPosition(column = 2, row = 0),
                TerminalPosition(column = 2, row = 1),
                TerminalPosition(column = 2, row = 2),
                TerminalPosition(column = 2, row = 2),
                TerminalPosition(column = 1, row = 2),
                TerminalPosition(column = 0, row = 2),
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 0, row = 1),
                TerminalPosition(column = 0, row = 2))
    }

    @Test
    fun shouldProperlyFillRectangle() {
        target.fillRectangle(
                topLeft = DEFAULT_POSITION,
                size = TerminalSize(2, 2),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 1, row = 0),
                TerminalPosition(column = 0, row = 1),
                TerminalPosition(column = 1, row = 1))
    }

    @Test
    fun shouldProperlyFillTriangle() {
        target.fillTriangle(
                p1 = DEFAULT_POSITION,
                p2 = TerminalPosition(3, 0),
                p3 = TerminalPosition(0, 3),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 3, row = 0),
                TerminalPosition(column = 2, row = 0),
                TerminalPosition(column = 1, row = 0),
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 2, row = 1),
                TerminalPosition(column = 1, row = 1),
                TerminalPosition(column = 0, row = 1),
                TerminalPosition(column = 1, row = 2),
                TerminalPosition(column = 0, row = 2),
                TerminalPosition(column = 0, row = 3))
    }

    @Test
    fun shouldProperlyFillIrregularTriangle() {
        target.fillTriangle(
                p1 = DEFAULT_POSITION,
                p2 = TerminalPosition(3, 1),
                p3 = TerminalPosition(2, 5),
                character = CHAR)

        assertThat(modifiedPoints).containsExactly(
                TerminalPosition(column = 0, row = 0),
                TerminalPosition(column = 0, row = 1),
                TerminalPosition(column = 1, row = 1),
                TerminalPosition(column = 2, row = 1),
                TerminalPosition(column = 3, row = 1),
                TerminalPosition(column = 0, row = 2),
                TerminalPosition(column = 1, row = 2),
                TerminalPosition(column = 2, row = 2),
                TerminalPosition(column = 3, row = 2),
                TerminalPosition(column = 1, row = 3),
                TerminalPosition(column = 2, row = 3),
                TerminalPosition(column = 1, row = 4),
                TerminalPosition(column = 2, row = 4),
                TerminalPosition(column = 2, row = 5))
    }

    companion object {
        val CHAR = TextCharacterBuilder.newBuilder().build()
    }

}