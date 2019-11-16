package org.hexworks.zircon.internal.game.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.CONTENT
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.GameAreaState
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class TopDownObliqueFrontProjectionStrategyTest {

    lateinit var target: TopDownObliqueFrontProjectionStrategy

    @Before
    fun setUp() {
        target = TopDownObliqueFrontProjectionStrategy()
    }

    @Test
    fun shouldOnlyCreateOneLevelWithOpaqueTiles() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 0, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(2, 0, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(3, 0, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E)))

        val result = target.projectGameArea(state).map { it.tiles }.toList()

        assertThat(result).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_A,
                Positions.create(1, 0) to OPAQUE_TILE_A,
                Positions.create(0, 1) to OPAQUE_TILE_D,
                Positions.create(1, 1) to OPAQUE_TILE_D))
    }

    @Test
    fun shouldCreateTwoLevelsWithTransparentFrontTile() {

        val state = DEFAULT_STATE.copy(
                blocks = mapOf(
                        pos(1, 0, 1) to block(
                                top = OPAQUE_TILE_A,
                                back = OPAQUE_TILE_B,
                                content = OPAQUE_TILE_C,
                                front = TRANSPARENT_TILE_B,
                                bottom = OPAQUE_TILE_E),
                        pos(2, 0, 1) to block(
                                top = OPAQUE_TILE_A,
                                back = OPAQUE_TILE_B,
                                content = OPAQUE_TILE_C,
                                front = OPAQUE_TILE_D,
                                bottom = OPAQUE_TILE_E),
                        pos(3, 0, 1) to block(
                                top = OPAQUE_TILE_A,
                                back = OPAQUE_TILE_B,
                                content = OPAQUE_TILE_C,
                                front = OPAQUE_TILE_D,
                                bottom = OPAQUE_TILE_E)))
        val result = target.projectGameArea(state).map { it.tiles }.toList()

        assertThat(result).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_A,
                Positions.create(1, 0) to OPAQUE_TILE_A,
                Positions.create(0, 1) to TRANSPARENT_TILE_B,
                Positions.create(1, 1) to OPAQUE_TILE_D), mapOf(
                Positions.create(0, 1) to OPAQUE_TILE_C))
    }

    @Ignore
    @Test
    fun shouldCreateThreeLevelsWithTransparentFrontAndContentTile() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 0, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = TRANSPARENT_TILE_A,
                        front = TRANSPARENT_TILE_B,
                        bottom = OPAQUE_TILE_E),
                pos(2, 0, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E)))

        val result = target.projectGameArea(state).map { it.tiles }.toList()

        assertThat(result).containsExactlyInAnyOrder(mapOf(
                Positions.create(0, 0) to OPAQUE_TILE_A,
                Positions.create(1, 0) to OPAQUE_TILE_A,
                Positions.create(0, 1) to TRANSPARENT_TILE_B,
                Positions.create(1, 1) to OPAQUE_TILE_D), mapOf(
                Positions.create(0, 1) to TRANSPARENT_TILE_A), mapOf(
                Positions.create(0, 1) to OPAQUE_TILE_E))
    }

    companion object {

        private val VISIBLE_SIZE_2X2X2 = Sizes.create3DSize(2, 2, 2)
        private val ACTUAL_SIZE_4X4X4 = Sizes.create3DSize(4, 4, 4)
        private val VISIBLE_OFFSET_1X1X1 = Positions.create3DPosition(1, 1, 1)

        val DEFAULT_STATE = GameAreaState<Tile, Block<Tile>>(
                actualSize = ACTUAL_SIZE_4X4X4,
                visibleSize = VISIBLE_SIZE_2X2X2,
                visibleOffset = VISIBLE_OFFSET_1X1X1,
                blocks = mapOf())

        val TRANSPARENT_TILE_A = Tiles.empty().withCharacter('a')
        val TRANSPARENT_TILE_B = Tiles.empty().withCharacter('b')

        val OPAQUE_TILE_A = Tiles.defaultTile().withCharacter('a')
        val OPAQUE_TILE_B = Tiles.defaultTile().withCharacter('b')
        val OPAQUE_TILE_C = Tiles.defaultTile().withCharacter('c')
        val OPAQUE_TILE_D = Tiles.defaultTile().withCharacter('d')
        val OPAQUE_TILE_E = Tiles.defaultTile().withCharacter('e')

        private fun pos(x: Int, y: Int, z: Int) = Positions.create3DPosition(x, y, z)

        private fun block(top: Tile, front: Tile, content: Tile, bottom: Tile, back: Tile) = Blocks
                .newBuilder<Tile>()
                .withTop(top)
                .withFront(front)
                .withContent(content)
                .withBottom(bottom)
                .withBottom(back)
                .withEmptyTile(Tiles.empty())
                .build()
    }
}