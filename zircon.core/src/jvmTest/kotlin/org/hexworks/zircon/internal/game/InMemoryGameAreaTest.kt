package org.hexworks.zircon.internal.game

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

// TODO: fix tests
class InMemoryGameAreaTest {

    lateinit var target: InMemoryGameArea<Tile, Block<Tile>>

    @Before
    fun setUp() {
        target = InMemoryGameArea(
                initialActualSize = HUGE_SIZE,
                initialVisibleSize = HUGE_SIZE,
                defaultBlock = BlockBuilder.newBuilder<Tile>()
                        .withEmptyTile(Tile.empty())
                        .withContent(Tile.empty())
                        .build())
        POSITIONS_IN_ORDER.shuffled().forEach {
            target.setBlockAt(it, BLOCK.build())
        }
    }

    @Ignore
    @Test
    fun shouldBeAbleToCreateGameAreaWithExtremeSize() {
        // setup does this
    }

    @Ignore
    @Test
    fun shouldFetchBlocksInProperOrder() {
        assertThat(target.fetchBlocks().map { it.key })
                .containsExactlyElementsOf(POSITIONS_IN_ORDER)

    }

    @Ignore
    @Test
    fun shouldFetchBlocksAtPositionAndSizeInProperOrder() {
        assertThat(target.fetchBlocksAt(
                offset = Position3D.create(1, 1, 1),
                size = Size3D.create(100, 100, 100))
                .map { it.key })
                .containsExactlyElementsOf(POSITIONS_IN_ORDER.drop(1).dropLast(1))

    }

    @Ignore
    @Test
    fun shouldFetchBlocksAtZLevelInProperOrder() {
        assertThat(target.fetchBlocksAtLevel(7)
                .map { it.key })
                .containsExactly(LEVEL_7_POS_0, LEVEL_7_POS_1)

    }

    @Ignore
    @Test
    fun shouldProperlyFetchBlockAtPosition() {
        assertThat(target.fetchBlockAt(LEVEL_7_POS_0).get())
                .isEqualTo(BlockBuilder.newBuilder<Tile>()
                        .withEmptyTile(Tile.empty())
                        .withContent(CONTENT).build())

    }

    @Ignore
    @Test
    fun shouldProperlySetBlockAtPosition() {
        target.setBlockAt(EMPTY_POSITION, OTHER_BLOCK.build())
        assertThat(target.fetchBlockAt(EMPTY_POSITION).get())
                .isEqualTo(BlockBuilder.newBuilder<Tile>()
                        .withEmptyTile(Tile.empty())
                        .withContent(OTHER_BLOCK_CONTENT)
                        .build())

    }

    @Ignore
    @Test
    fun shouldProperlyFetchEmptyBlockAtEmptyPosition() {
        assertThat(target.fetchBlockAt(EMPTY_POSITION).isPresent).isFalse()
    }

    @Ignore
    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToSetBlockAtPositionWhichIsNotWithinSize() {
        val badPos = Position3D.create(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        target.setBlockAt(badPos, BLOCK.build())
    }

    companion object {

        private val BOTTOM_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.RED)
        private val MID_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.GREEN)
        private val TOP_CHAR = Tile.defaultTile().withBackgroundColor(ANSITileColor.BLUE)

        private val POS_FOR_LAYER_0 = Position3D.create(2, 1, 5)
        private val POS_FOR_LAYER_1 = Position3D.create(3, 1, 6)


        val CONTENT = BOTTOM_CHAR
        val BLOCK = BlockBuilder.newBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .withContent(CONTENT)

        val OTHER_BLOCK_CONTENT = TileBuilder.newBuilder().withBackgroundColor(ANSITileColor.RED).build()
        val OTHER_BLOCK = BlockBuilder.newBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .withContent(OTHER_BLOCK_CONTENT)

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

        val EXPECTED_LAYER_0 = mapOf(
                Position.create(0, 0) to Tile.empty(),
                Position.create(1, 0) to BOTTOM_CHAR,
                Position.create(2, 0) to Tile.empty(),
                Position.create(0, 1) to Tile.empty(),
                Position.create(1, 1) to Tile.empty(),
                Position.create(2, 1) to Tile.empty())

        val EXPECTED_LAYER_1 = EXPECTED_LAYER_0.toMutableMap().also {
            it[Position.create(1, 0)] = MID_CHAR
        }
        val EXPECTED_LAYER_2 = EXPECTED_LAYER_0.toMutableMap().also {
            it[Position.create(1, 0)] = TOP_CHAR
        }
        val EXPECTED_LAYER_3 = mapOf(
                Position.create(0, 0) to Tile.empty(),
                Position.create(1, 0) to Tile.empty(),
                Position.create(2, 0) to BOTTOM_CHAR,
                Position.create(0, 1) to Tile.empty(),
                Position.create(1, 1) to Tile.empty(),
                Position.create(2, 1) to Tile.empty())
        val EXPECTED_LAYER_4 = EXPECTED_LAYER_3.toMutableMap().also {
            it[Position.create(2, 0)] = MID_CHAR
        }
        val EXPECTED_LAYER_5 = EXPECTED_LAYER_3.toMutableMap().also {
            it[Position.create(2, 0)] = TOP_CHAR
        }

        val HUGE_SIZE = Size3D.create(Int.MAX_VALUE - 1, Int.MAX_VALUE - 1, Int.MAX_VALUE - 1)
    }
}
