package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
class TopDownFrontGameAreaRendererTest {

    lateinit var target: TopDownFrontGameAreaRenderer

    @BeforeTest
    fun setUp() {
        target = TopDownFrontGameAreaRenderer()
    }

    @Test
    fun When_projection_sequence_is_requested_Then_it_is_properly_returned() {
        assertEquals(
                expected = listOf(
                        Position3D.create(2, 1, 3),
                        Position3D.create(2, 2, 3),
                        Position3D.create(2, 3, 3),
                        Position3D.create(2, 4, 3),
                        Position3D.create(2, 4, 3),
                        Position3D.create(2, 4, 2),

                        ),
                actual = target.generateProjectionSequence(
                        visibleSize = Size3D.create(3, 4, 2),
                        visibleOffset = Position3D.create(2, 1, 2),
                        x = 2,
                        height = 5
                ).map { it.first().first }.toList()
        )
    }

    @Test
    fun When_front_sequence_is_requested_Then_it_is_properly_returned() {
        assertEquals(
                expected = listOf(
                        Position3D.create(0, 4, 2) to FRONT,
                        Position3D.create(0, 4, 2) to BOTTOM,
                        Position3D.create(0, 4, 1) to TOP,
                        Position3D.create(0, 4, 1) to BACK,
                        Position3D.create(0, 3, 1) to FRONT,
                        Position3D.create(0, 3, 1) to BOTTOM,
                        Position3D.create(0, 3, 0) to TOP,
                        Position3D.create(0, 3, 0) to BACK,
                        Position3D.create(0, 2, 0) to FRONT,
                        Position3D.create(0, 2, 0) to BOTTOM,
                ),
                actual = target.generateFrontSequence(
                        visibleOffset = Position3D.defaultPosition(),
                        startPos = Position3D.create(0, 4, 2)
                ).toList()
        )
    }

    @Test
    fun When_front_sequence_is_requested_at_max_z_Then_it_is_properly_returned() {
        assertEquals(
                expected = listOf(
                        Position3D.create(0, 2, 2) to FRONT,
                        Position3D.create(0, 2, 2) to BOTTOM,
                        Position3D.create(0, 2, 1) to TOP,
                        Position3D.create(0, 2, 1) to BACK,
                        Position3D.create(0, 1, 1) to FRONT,
                        Position3D.create(0, 1, 1) to BOTTOM,
                        Position3D.create(0, 1, 0) to TOP,
                        Position3D.create(0, 1, 0) to BACK,
                        Position3D.create(0, 0, 0) to FRONT,
                        Position3D.create(0, 0, 0) to BOTTOM,
                ),
                actual = target.generateFrontSequence(
                        visibleOffset = Position3D.defaultPosition(),
                        startPos = Position3D.create(0, 2, 2)
                ).toList()
        )
    }

    @Test
    fun When_top_sequence_is_requested_Then_it_is_properly_returned() {
        assertEquals(
                expected = listOf(
                        Position3D.create(0, 2, 4) to TOP,
                        Position3D.create(0, 2, 4) to BACK,
                        Position3D.create(0, 1, 4) to FRONT,
                        Position3D.create(0, 1, 4) to BOTTOM,
                        Position3D.create(0, 1, 3) to TOP,
                        Position3D.create(0, 1, 3) to BACK,
                        Position3D.create(0, 0, 3) to FRONT,
                        Position3D.create(0, 0, 3) to BOTTOM,
                        Position3D.create(0, 0, 2) to TOP,
                        Position3D.create(0, 0, 2) to BACK
                ),
                actual = target.generateTopSequence(
                        visibleOffset = Position3D.defaultPosition(),
                        startPos = Position3D.create(0, 2, 4)
                ).toList()
        )
    }

    @Test
    fun When_top_sequence_is_requested_at_max_z_Then_it_is_properly_returned() {
        assertEquals(
                expected = listOf(
                        Position3D.create(0, 2, 2) to TOP,
                        Position3D.create(0, 2, 2) to BACK,
                        Position3D.create(0, 1, 2) to FRONT,
                        Position3D.create(0, 1, 2) to BOTTOM,
                        Position3D.create(0, 1, 1) to TOP,
                        Position3D.create(0, 1, 1) to BACK,
                        Position3D.create(0, 0, 1) to FRONT,
                        Position3D.create(0, 0, 1) to BOTTOM,
                        Position3D.create(0, 0, 0) to TOP,
                        Position3D.create(0, 0, 0) to BACK,
                ),
                actual = target.generateTopSequence(
                        visibleOffset = Position3D.defaultPosition(),
                        startPos = Position3D.create(0, 2, 2)
                ).toList()
        )
    }
}
