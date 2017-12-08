package org.codetome.zircon.api.beta.component

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.junit.Before
import org.junit.Test

class TextImageGameAreaTest {

    lateinit var target: TextImageGameArea

    @Before
    fun setUp() {
        target = TextImageGameArea(SIZE, LEVELS)
    }

    @Test
    fun shouldProperlyReturnCharactersFromLevel0() {
        assertThat(target.getCharactersAt(Position3D.from2DPosition(OTHER_CHAR_POS, 0)))
                .containsExactly(OTHER_CHAR)
    }

    @Test
    fun shouldProperlyReturnCharactersFromLevel1() {
        assertThat(target.getCharactersAt(Position3D.from2DPosition(OTHER_CHAR_POS, 1)))
                .containsExactly(OTHER_CHAR, OTHER_CHAR)
    }

    @Test
    fun shouldProperlyReturnSegmentFromLevel0() {
        val result = target.getSegmentAt(
                offset = Position3D.from2DPosition(OTHER_CHAR_POS, 0),
                size = Size.of(2, 2))
        assertThat(result.layers).hasSize(1)
        assertThat(result.layers.first().fetchCells().toList())
                .containsExactly(
                        Cell(Position.of(0, 0), OTHER_CHAR),
                        Cell(Position.of(1, 0), LEVEL0_FILLER),
                        Cell(Position.of(0, 1), LEVEL0_FILLER),
                        Cell(Position.of(1, 1), LEVEL0_FILLER))
    }

    @Test
    fun shouldProperlyReturnSegmentFromLevel1() {
        val result = target.getSegmentAt(
                offset = Position3D.from2DPosition(OTHER_CHAR_POS, 1),
                size = Size.of(2, 2))
        assertThat(result.layers).hasSize(2)
        assertThat(result.layers[0].fetchCells().toList())
                .containsExactly(
                        Cell(Position.of(0, 0), OTHER_CHAR),
                        Cell(Position.of(1, 0), LEVEL1_0_FILLER),
                        Cell(Position.of(0, 1), LEVEL1_0_FILLER),
                        Cell(Position.of(1, 1), LEVEL1_0_FILLER))
        assertThat(result.layers[1].fetchCells().toList())
                .containsExactly(
                        Cell(Position.of(0, 0), OTHER_CHAR),
                        Cell(Position.of(1, 0), LEVEL1_1_FILLER),
                        Cell(Position.of(0, 1), LEVEL1_1_FILLER),
                        Cell(Position.of(1, 1), LEVEL1_1_FILLER))
    }

    companion object {

        val OTHER_CHAR = TextCharacterBuilder.newBuilder()
                .character('z')
                .build()
        val OTHER_CHAR_POS = Position.of(1, 2)
        val COLUMNS = 4
        val ROWS = 5
        val LEVEL_SIZE = Size.of(COLUMNS, ROWS)
        val LEVEL0_FILLER = TextCharacterBuilder.newBuilder()
                .character('a')
                .build()
        val LEVEL0 = TextImageBuilder.newBuilder()
                .filler(LEVEL0_FILLER)
                .size(LEVEL_SIZE)
                .build()
        val LEVEL1_0_FILLER = TextCharacterBuilder.newBuilder()
                .character('b')
                .build()
        val LEVEL1_0 = TextImageBuilder.newBuilder()
                .filler(LEVEL1_0_FILLER)
                .size(LEVEL_SIZE)
                .build()
        val LEVEL1_1_FILLER = TextCharacterBuilder.newBuilder()
                .character('c')
                .build()
        val LEVEL1_1 = TextImageBuilder.newBuilder()
                .filler(LEVEL1_1_FILLER)
                .size(LEVEL_SIZE)
                .build()

        init {
            LEVEL0.setCharacterAt(OTHER_CHAR_POS, OTHER_CHAR)
            LEVEL1_0.setCharacterAt(OTHER_CHAR_POS, OTHER_CHAR)
            LEVEL1_1.setCharacterAt(OTHER_CHAR_POS, OTHER_CHAR)
        }

        val SIZE = Size3D.of(COLUMNS, ROWS, 2)
        val LEVELS = mapOf(
                Pair(0, listOf(LEVEL0)),
                Pair(1, listOf(LEVEL1_0, LEVEL1_1)))
    }
}