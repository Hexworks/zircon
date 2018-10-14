package org.hexworks.zircon.internal.game

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.junit.Before
import org.junit.Test

class InMemoryGameAreaTest {

    lateinit var target: InMemoryGameArea

    @Before
    fun setUp() {
        target = InMemoryGameArea(HUGE_SIZE, 3)
        POSITIONS_IN_ORDER.shuffled().forEach {
            target.setBlockAt(it, BLOCK.withPosition(it).build())
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
                offset = Position3D.create(1, 1, 1),
                size = Size3D.create(100, 100, 100))
                .map { it.position })
                .containsExactlyElementsOf(POSITIONS_IN_ORDER.drop(1).dropLast(1))

    }

    @Test
    fun shouldFetchBlocksAtZLevelInProperOrder() {
        assertThat(target.fetchBlocksAtLevel(7)
                .map { it.position })
                .containsExactly(LEVEL_7_POS_0, LEVEL_7_POS_1)

    }

    @Test
    fun shouldProperlyFetchBlockAtPosition() {
        assertThat(target.fetchBlockAt(LEVEL_7_POS_0).get())
                .isEqualTo(BlockBuilder.create().withPosition(LEVEL_7_POS_0).withLayers(BLOCK_LAYERS    ).build())

    }

    @Test
    fun shouldProperlySetBlockAtPosition() {
        target.setBlockAt(EMPTY_POSITION, OTHER_BLOCK.withPosition(EMPTY_POSITION).build())
        assertThat(target.fetchBlockAt(EMPTY_POSITION).get())
                .isEqualTo(BlockBuilder.create()
                        .withPosition(EMPTY_POSITION)
                        .withLayers(OTHER_BLOCK_LAYER, OTHER_BLOCK_LAYER, OTHER_BLOCK_LAYER)
                        .build())

    }

    @Test
    fun shouldProperlyFetchEmptyBlockAtEmptyPosition() {
        assertThat(target.fetchBlockAt(EMPTY_POSITION).isPresent).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToSetBlockAtPositionWhichIsNotWithinSize() {
        val badPos = Position3D.create(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        target.setBlockAt(badPos, BLOCK.withPosition(badPos).build())
    }

    @Test
    fun shouldProperlyFetchLayers() {
        val result = target.fetchLayersAt(
                offset = Position3D.from2DPosition(Position.offset1x1(), 5),
                size = Size3D.from2DSize(Size.create(3, 2), 2))

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

        private val BOTTOM_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.RED)
        private val MID_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.GREEN)
        private val TOP_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.BLUE)

        private val POS_FOR_LAYER_0 = Position3D.create(2, 1, 5)
        private val POS_FOR_LAYER_1 = Position3D.create(3, 1, 6)


        val BLOCK_LAYERS = listOf(BOTTOM_CHAR, MID_CHAR, TOP_CHAR)
        val BLOCK = BlockBuilder.create()
                .withLayers(BLOCK_LAYERS)

        val OTHER_BLOCK_LAYER = TileBuilder.newBuilder().withBackgroundColor(ANSITileColor.RED).build()
        val OTHER_BLOCK = BlockBuilder.create()
                .withLayers(OTHER_BLOCK_LAYER, OTHER_BLOCK_LAYER, OTHER_BLOCK_LAYER)

        val EMPTY_POSITION = Position3D.create(323, 123, 654)

        val LEVEL_7_POS_0 = Position3D.create(9, 3, 7)
        val LEVEL_7_POS_1 = Position3D.create(9, 4, 7)

        val POSITIONS_IN_ORDER = listOf(
                Position3D.create(0, 0, 0),
                Position3D.create(1, 1, 4),
                POS_FOR_LAYER_0,
                POS_FOR_LAYER_1,
                LEVEL_7_POS_0,
                LEVEL_7_POS_1,
                Position3D.create(999, 999, 999))

        val EXPECTED_LAYER_0 = listOf(
                Cell.create(Position.create(0, 0), Tile.empty()),
                Cell.create(Position.create(1, 0), BOTTOM_CHAR),
                Cell.create(Position.create(2, 0), Tile.empty()),
                Cell.create(Position.create(0, 1), Tile.empty()),
                Cell.create(Position.create(1, 1), Tile.empty()),
                Cell.create(Position.create(2, 1), Tile.empty()))

        val EXPECTED_LAYER_1 = EXPECTED_LAYER_0.toMutableList().also {
            it[1] = Cell.create(it[1].position, MID_CHAR)
        }
        val EXPECTED_LAYER_2 = EXPECTED_LAYER_0.toMutableList().also {
            it[1] = Cell.create(it[1].position, TOP_CHAR)
        }
        val EXPECTED_LAYER_3 = listOf(
                Cell.create(Position.create(0, 0), Tile.empty()),
                Cell.create(Position.create(1, 0), Tile.empty()),
                Cell.create(Position.create(2, 0), BOTTOM_CHAR),
                Cell.create(Position.create(0, 1), Tile.empty()),
                Cell.create(Position.create(1, 1), Tile.empty()),
                Cell.create(Position.create(2, 1), Tile.empty()))
        val EXPECTED_LAYER_4 = EXPECTED_LAYER_3.toMutableList().also {
            it[2] = Cell.create(it[2].position, MID_CHAR)
        }
        val EXPECTED_LAYER_5 = EXPECTED_LAYER_3.toMutableList().also {
            it[2] = Cell.create(it[2].position, TOP_CHAR)
        }

        val HUGE_SIZE = Size3D.create(Int.MAX_VALUE - 1, Int.MAX_VALUE - 1, Int.MAX_VALUE - 1)
    }
}
