package org.codetome.zircon.api.beta.component

import org.assertj.core.api.Assertions.assertThat
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


    companion object {

        val OTHER_CHAR = TextCharacterBuilder.newBuilder()
                .character('z')
                .build()
        val OTHER_CHAR_POS = Position.of(2, 3)
        val COLUMNS = 4
        val ROWS = 5
        val LEVEL_SIZE = Size.of(COLUMNS, ROWS)
        val LEVEL0_FILLER = 'a'
        val LEVEL0 = TextImageBuilder.newBuilder()
                .filler(TextCharacterBuilder.newBuilder()
                        .character(LEVEL0_FILLER)
                        .build())
                .size(LEVEL_SIZE)
                .build()
        val LEVEL1_0_FILLER = 'b'
        val LEVEL1_0 = TextImageBuilder.newBuilder()
                .filler(TextCharacterBuilder.newBuilder()
                        .character(LEVEL1_0_FILLER)
                        .build())
                .size(LEVEL_SIZE)
                .build()
        val LEVEL1_1_FILLER = 'c'
        val LEVEL1_1 = TextImageBuilder.newBuilder()
                .filler(TextCharacterBuilder.newBuilder()
                        .character(LEVEL1_1_FILLER)
                        .build())
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