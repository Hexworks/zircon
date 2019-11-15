package org.hexworks.zircon.internal.game.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameAreaState
import org.junit.Before
import org.junit.Test


class TopDownProjectionStrategyTest {

    lateinit var target: TopDownProjectionStrategy

    @Before
    fun setUp() {
        target = TopDownProjectionStrategy()
    }

    @Test
    fun shouldProjectProperlyFromPositionWithOpaqueBlock() {
        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to OPAQUE_TOP_BLOCK))
        val result = target.renderSequence(pos(1, 1, 2))

        assertThat(result.take(4).toList()).containsExactly(
                pos(1, 1, 2) to BlockTileType.TOP,
                pos(1, 1, 2) to BlockTileType.CONTENT,
                pos(1, 1, 2) to BlockTileType.BOTTOM,
                pos(1, 1, 1) to BlockTileType.TOP)
    }

    @Test
    fun shouldOnlyCreateOneLevelWithOpaqueTiles() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to OPAQUE_TOP_BLOCK,
                pos(2, 1, 2) to OPAQUE_TOP_BLOCK,
                pos(1, 1, 1) to OPAQUE_TOP_BLOCK))

        val result = target.projectGameArea(state).toList()
        val windowSize = size(state.visibleSize.xLength, state.visibleSize.zLength)

        assertThat(result.map { it.tiles }).containsExactlyInAnyOrder(
                mapOf(
                        Positions.create(0, 0) to OPAQUE_TILE_A,
                        Positions.create(1, 0) to OPAQUE_TILE_A))
    }

    companion object {

        val ACTUAL_SIZE_4X4X4 = Sizes.create3DSize(4, 4, 4)
        val VISIBLE_SIZE_3X2X2 = Sizes.create3DSize(3, 2, 2)
        val VISIBLE_OFFSET_1_1_1 = Positions.create3DPosition(1, 1, 1)

        val DEFAULT_STATE = GameAreaState<Tile, Block<Tile>>(
                blocks = mapOf(),
                actualSize = ACTUAL_SIZE_4X4X4,
                visibleSize = VISIBLE_SIZE_3X2X2,
                visibleOffset = VISIBLE_OFFSET_1_1_1)

        val TRANSPARENT_TILE_A = Tiles.empty().withCharacter('a')
        val TRANSPARENT_TILE_B = Tiles.empty().withCharacter('b')
        val TRANSPARENT_TILE_C = Tiles.empty().withCharacter('c')
        val TRANSPARENT_TILE_D = Tiles.empty().withCharacter('d')
        val TRANSPARENT_TILE_E = Tiles.empty().withCharacter('e')

        val OPAQUE_TILE_A = Tiles.defaultTile().withCharacter('a')
        val OPAQUE_TILE_B = Tiles.defaultTile().withCharacter('b')
        val OPAQUE_TILE_C = Tiles.defaultTile().withCharacter('c')
        val OPAQUE_TILE_D = Tiles.defaultTile().withCharacter('d')
        val OPAQUE_TILE_E = Tiles.defaultTile().withCharacter('e')

        val OPAQUE_TOP_BLOCK = block(
                top = OPAQUE_TILE_A,
                content = OPAQUE_TILE_B,
                bottom = OPAQUE_TILE_C)

        val TRANSPARENT_TOP_BLOCK = block(
                top = TRANSPARENT_TILE_A,
                content = OPAQUE_TILE_B,
                bottom = OPAQUE_TILE_C)

        private fun size(width: Int, height: Int) = Sizes.create(width, height)

        private fun pos(x: Int, y: Int, z: Int) = Positions.create3DPosition(x, y, z)

        private fun block(top: Tile, content: Tile, bottom: Tile) = Blocks
                .newBuilder<Tile>()
                .withTop(top)
                .withContent(content)
                .withBottom(bottom)
                .withEmptyTile(Tiles.empty())
                .build()
    }
}