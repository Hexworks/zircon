package org.codetome.zircon.internal.game

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Block
import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder.Companion.DEFAULT_CHARACTER
import org.codetome.zircon.api.builder.TextCharacterBuilder.Companion.EMPTY
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.Size3D
import org.junit.Before
import org.junit.Test

class InMemoryGameAreaTest {

    lateinit var target: InMemoryGameArea

    @Before
    fun setUp() {
        target = InMemoryGameArea(HUGE_SIZE, 3)
        POSITIONS_IN_ORDER.shuffled().forEach {
            target.setBlockAt(it, BLOCK)
        }
    }

    @Test
    fun shouldBeAbleToCreateGameAreaWithExtremeSize() {
        // setup does this
    }

    @Test
    fun shouldFetchBlocksInProperOrder() {
        assertThat(target.fetchBlocks().map { it.position })
                .containsExactlyElementsOf(POSITIONS_IN_ORDER)

    }

    @Test
    fun shouldFetchBlocksAtPositionAndSizeInProperOrder() {
        assertThat(target.fetchBlocksAt(
                offset = Position3D.of(1, 1, 1),
                size = Size3D.of(100, 100, 100))
                .map { it.position })
                .containsExactlyElementsOf(POSITIONS_IN_ORDER.drop(1).dropLast(1))

    }

    @Test
    fun shouldFetchBlocksAtZLevelInProperOrder() {
        assertThat(target.fetchBlocksAt(7)
                .map { it.position })
                .containsExactly(LEVEL_7_POS_0, LEVEL_7_POS_1)

    }

    @Test
    fun shouldProperlyFetchBlockAtPosition() {
        assertThat(target.fetchBlockAt(LEVEL_7_POS_0).get())
                .isEqualTo(Block(position = LEVEL_7_POS_0, layers = BLOCK.toMutableList()))

    }

    @Test
    fun shouldProperlySetBlockAtPosition() {
        target.setBlockAt(EMPTY_POSITION, OTHER_BLOCK)
        assertThat(target.fetchBlockAt(EMPTY_POSITION).get())
                .isEqualTo(Block(position = EMPTY_POSITION, layers = OTHER_BLOCK.plus(EMPTY).plus(EMPTY).toMutableList()))

    }

    @Test
    fun shouldProperlyFetchEmptyBlockAtEmptyPosition() {
        assertThat(target.fetchBlockAt(EMPTY_POSITION)).isNotPresent
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToSetBlockAtPositionWhichIsNotWithinSize() {
        target.setBlockAt(Position3D.of(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE), BLOCK)
    }

    @Test
    fun shouldProperlyFetchLayers() {
        val result = target.fetchLayersAt(
                offset = Position3D.from2DPosition(Position.OFFSET_1x1, 5),
                size = Size3D.from2DSize(Size.of(3, 2), 2))

        assertThat(result).hasSize(6)
        val cells = result.map {
            it.fetchCells()
        }
        assertThat(cells[0]).containsExactlyElementsOf(EXPECTED_LAYER_0)
        assertThat(cells[1]).containsExactlyElementsOf(EXPECTED_LAYER_1)
        assertThat(cells[2]).containsExactlyElementsOf(EXPECTED_LAYER_2)
        assertThat(cells[3]).containsExactlyElementsOf(EXPECTED_LAYER_3)
        assertThat(cells[4]).containsExactlyElementsOf(EXPECTED_LAYER_4)
        assertThat(cells[5]).containsExactlyElementsOf(EXPECTED_LAYER_5)

    }

    companion object {

        val BOTTOM_CHAR = TextCharacterBuilder.DEFAULT_CHARACTER.withBackgroundColor(ANSITextColor.RED)
        val MID_CHAR = TextCharacterBuilder.DEFAULT_CHARACTER.withBackgroundColor(ANSITextColor.GREEN)
        val TOP_CHAR = TextCharacterBuilder.DEFAULT_CHARACTER.withBackgroundColor(ANSITextColor.BLUE)


        val BLOCK = listOf(BOTTOM_CHAR, MID_CHAR, TOP_CHAR)
        val OTHER_BLOCK = listOf(TextCharacterBuilder.newBuilder().backgroundColor(ANSITextColor.RED).build())

        val EMPTY_POSITION = Position3D.of(323, 123, 654)

        val LEVEL_7_POS_0 = Position3D.of(9, 3, 7)
        val LEVEL_7_POS_1 = Position3D.of(9, 4, 7)

        val POS_FOR_LAYER_0 = Position3D.of(2, 1, 5)
        val POS_FOR_LAYER_1 = Position3D.of(3, 1, 6)

        val POSITIONS_IN_ORDER = listOf(
                Position3D.of(0, 0, 0),
                Position3D.of(1, 1, 4),
                POS_FOR_LAYER_0,
                POS_FOR_LAYER_1,
                LEVEL_7_POS_0,
                LEVEL_7_POS_1,
                Position3D.of(999, 999, 999))

        val EXPECTED_LAYER_0 = listOf(
                Cell(Position.of(0, 0), EMPTY),
                Cell(Position.of(1, 0), BOTTOM_CHAR),
                Cell(Position.of(2, 0), EMPTY),
                Cell(Position.of(0, 1), EMPTY),
                Cell(Position.of(1, 1), EMPTY),
                Cell(Position.of(2, 1), EMPTY))

        val EXPECTED_LAYER_1 = EXPECTED_LAYER_0.toMutableList().also {
            it[1] = Cell(it[1].position, MID_CHAR)
        }
        val EXPECTED_LAYER_2 = EXPECTED_LAYER_0.toMutableList().also {
            it[1] = Cell(it[1].position, TOP_CHAR)
        }
        val EXPECTED_LAYER_3 = listOf(
                Cell(Position.of(0, 0), EMPTY),
                Cell(Position.of(1, 0), EMPTY),
                Cell(Position.of(2, 0), BOTTOM_CHAR),
                Cell(Position.of(0, 1), EMPTY),
                Cell(Position.of(1, 1), EMPTY),
                Cell(Position.of(2, 1), EMPTY))
        val EXPECTED_LAYER_4 = EXPECTED_LAYER_3.toMutableList().also {
            it[2] = Cell(it[2].position, MID_CHAR)
        }
        val EXPECTED_LAYER_5 = EXPECTED_LAYER_3.toMutableList().also {
            it[2] = Cell(it[2].position, TOP_CHAR)
        }

        val HUGE_SIZE = Size3D.of(Int.MAX_VALUE - 1, Int.MAX_VALUE - 1, Int.MAX_VALUE - 1)
    }
}
