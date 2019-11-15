package org.hexworks.zircon.internal.game.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileComposite
import org.hexworks.zircon.api.game.GameAreaState
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.MockitoAnnotations

class TopDownObliqueProjectionStrategyTest {

    lateinit var target: TopDownObliqueProjectionStrategy

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = TopDownObliqueProjectionStrategy()
    }

    @Ignore
    @Test
    fun shouldOnlyCreateOneLevelWithOpaqueTiles() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 0) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E)))

        val result = target.projectGameArea(state).toList()
        val windowSize = size(state.visibleSize.xLength, state.visibleSize.zLength)

        assertThat(result).containsExactlyInAnyOrder(
                mapOf(Positions.create(0, 1) to OPAQUE_TILE_A)
                        .toTileComposite(windowSize))
    }

    @Ignore
    @Test
    fun shouldCreateTwoLevelsWithTransparentFrontTile() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = TRANSPARENT_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 0) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E)))
        val result = target.projectGameArea(state).toList()
        val windowSize = size(state.visibleSize.xLength, state.visibleSize.zLength)

        assertThat(result).containsExactlyInAnyOrder(
                mapOf(Positions.create(0, 1) to TRANSPARENT_TILE_D).toTileComposite(windowSize),
                mapOf(Positions.create(0, 1) to OPAQUE_TILE_C).toTileComposite(windowSize))
    }

    @Ignore
    @Test
    fun shouldCreateThreeLevelsWithTransparentFrontAndContentTile() {

        val state = DEFAULT_STATE.copy(blocks = mapOf(
                pos(1, 1, 2) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = TRANSPARENT_TILE_C,
                        front = TRANSPARENT_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 1) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E),
                pos(1, 1, 0) to block(
                        top = OPAQUE_TILE_A,
                        back = OPAQUE_TILE_B,
                        content = OPAQUE_TILE_C,
                        front = OPAQUE_TILE_D,
                        bottom = OPAQUE_TILE_E)))

        val result = target.projectGameArea(state).toList()
        val windowSize = size(state.visibleSize.xLength, state.visibleSize.zLength)

        assertThat(result).containsExactlyInAnyOrder(
                mapOf(Positions.create(0, 0) to TRANSPARENT_TILE_D).toTileComposite(windowSize),
                mapOf(Positions.create(0, 0) to TRANSPARENT_TILE_C).toTileComposite(windowSize),
                mapOf(Positions.create(0, 0) to OPAQUE_TILE_E).toTileComposite(windowSize))
    }

    companion object {

        val VISIBLE_SIZE_2X2X2 = Sizes.create3DSize(2, 2, 2)
        val ACTUAL_SIZE_2X2X4 = Sizes.create3DSize(2, 2, 4)
        val VISIBLE_OFFSET_1X0X1 = Positions.create3DPosition(1, 0, 1)

        val DEFAULT_STATE = GameAreaState<Tile, Block<Tile>>(
                actualSize = ACTUAL_SIZE_2X2X4,
                visibleSize = VISIBLE_SIZE_2X2X2,
                visibleOffset = VISIBLE_OFFSET_1X0X1,
                blocks = mapOf())

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

        private fun size(width: Int, height: Int) = Sizes.create(width, height)

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