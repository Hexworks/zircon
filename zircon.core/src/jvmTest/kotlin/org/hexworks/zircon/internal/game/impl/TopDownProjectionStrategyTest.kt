package org.hexworks.zircon.internal.game.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.GameAreaState
import org.junit.Before
import org.junit.Test


class TopDownProjectionStrategyTest {

    lateinit var target: TopDownProjectionStrategy

    @Before
    fun setUp() {
        target = TopDownProjectionStrategy()
    }

    @Test
    fun shouldCreateRenderSequenceProperly() {
        val result = target.createRenderingSequence(pos(1, 1, 2))

        assertThat(result.take(4).toList()).containsExactly(
                pos(1, 1, 2) to BlockTileType.TOP,
                pos(1, 1, 2) to BlockTileType.CONTENT,
                pos(1, 1, 2) to BlockTileType.BOTTOM,
                pos(1, 1, 1) to BlockTileType.TOP)
    }

    @Test
    fun shouldOnlyCreateOneLevelWithOpaqueTiles() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to OPAQUE_BLOCK_A_B_C,
                pos(2, 1, 2) to OPAQUE_BLOCK_A_B_C,
                pos(1, 1, 1) to OPAQUE_BLOCK_A_B_C))

        val result = target.projectGameArea(state).toList()

        assertThat(result.map { it.tiles }).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_A,
                Positions.create(1, 0) to OPAQUE_TILE_A))
    }

    @Test
    fun shouldCreateTwoLevelsWithEmptyLayerBetweenTransparentTiles() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to OPAQUE_BLOCK_A_B_C,
                pos(2, 1, 2) to TRANSPARENT_TOP_EMPTY_CONTENT_BLOCK_A_C,
                pos(1, 1, 1) to OPAQUE_BLOCK_A_B_C))

        val result = target.projectGameArea(state).toList()

        assertThat(result.map { it.tiles }).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_A,
                Positions.create(1, 0) to TRANSPARENT_TILE_A), mapOf(
                Positions.create(1, 0) to OPAQUE_TILE_C))
    }

    @Test
    fun shouldCreateFourLevelsWithTransparentWholeBlockOverOpaqueBlock() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to TRANSPARENT_WHOLE_BLOCK,
                pos(2, 1, 2) to OPAQUE_BLOCK_A_B_C,
                pos(1, 1, 1) to OPAQUE_BLOCK_D_E_F))

        val result = target.projectGameArea(state).toList()

        assertThat(result.map { it.tiles }).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to TRANSPARENT_TILE_A,
                Positions.create(1, 0) to OPAQUE_TILE_A), mapOf(
                Positions.create(0, 0) to TRANSPARENT_TILE_B), mapOf(
                Positions.create(0, 0) to TRANSPARENT_TILE_C), mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_D))
    }

    @Test
    fun shouldCreateThreeLevelsWithVaryingEmptiness() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to TRANSPARENT_TOP_EMPTY_CONTENT_BLOCK_A_C,
                pos(2, 1, 2) to EMPTY_TOP_TRANSPARENT_CONTENT_BLOCK_B_D))

        val result = target.projectGameArea(state).toList()

        assertThat(result.map { it.tiles }).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to TRANSPARENT_TILE_A), mapOf(
                Positions.create(1, 0) to TRANSPARENT_TILE_B), mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_C,
                Positions.create(1, 0) to OPAQUE_TILE_D))
    }

    companion object {

        private val ACTUAL_SIZE_4X4X4 = Sizes.create3DSize(4, 4, 4)
        private val VISIBLE_SIZE_3X2X2 = Sizes.create3DSize(3, 2, 2)
        private val VISIBLE_OFFSET_1_1_1 = Positions.create3DPosition(1, 1, 1)

        val DEFAULT_STATE = GameAreaState<Tile, Block<Tile>>(
                blocks = mapOf(),
                actualSize = ACTUAL_SIZE_4X4X4,
                visibleSize = VISIBLE_SIZE_3X2X2,
                visibleOffset = VISIBLE_OFFSET_1_1_1)

        val TRANSPARENT_TILE_A = Tiles.empty().withCharacter('a')
        val TRANSPARENT_TILE_B = Tiles.empty().withCharacter('b')
        val TRANSPARENT_TILE_C = Tiles.empty().withCharacter('c')

        val OPAQUE_TILE_A = Tiles.defaultTile().withCharacter('a')
        val OPAQUE_TILE_B = Tiles.defaultTile().withCharacter('b')
        val OPAQUE_TILE_C = Tiles.defaultTile().withCharacter('c')
        val OPAQUE_TILE_D = Tiles.defaultTile().withCharacter('d')
        val OPAQUE_TILE_E = Tiles.defaultTile().withCharacter('e')
        val OPAQUE_TILE_F = Tiles.defaultTile().withCharacter('f')

        val OPAQUE_BLOCK_A_B_C = block(
                top = OPAQUE_TILE_A,
                content = OPAQUE_TILE_B,
                bottom = OPAQUE_TILE_C)

        val OPAQUE_BLOCK_D_E_F = block(
                top = OPAQUE_TILE_D,
                content = OPAQUE_TILE_E,
                bottom = OPAQUE_TILE_F)

        val TRANSPARENT_TOP_EMPTY_CONTENT_BLOCK_A_C = block(
                top = TRANSPARENT_TILE_A,
                content = Tiles.empty(),
                bottom = OPAQUE_TILE_C)

        val EMPTY_TOP_TRANSPARENT_CONTENT_BLOCK_B_D = block(
                top = Tiles.empty(),
                content = TRANSPARENT_TILE_B,
                bottom = OPAQUE_TILE_D)

        val TRANSPARENT_WHOLE_BLOCK = block(
                top = TRANSPARENT_TILE_A,
                content = TRANSPARENT_TILE_B,
                bottom = TRANSPARENT_TILE_C)

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