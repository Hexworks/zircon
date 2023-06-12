package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PositionsTest {

    private var componentStub: Component? = null

    @BeforeTest
    fun setUp() {
        componentStub = buildLabel {
            position = POSITION_2X3
            preferredSize = SIZE_3X4
        }
    }

    @Test
    fun shouldProperlyReturnTopLeftCorner() {
        assertEquals(Position.topLeftCorner(), Position.create(0, 0))
    }

    @Test
    fun shouldProperlyReturnOffset1x1() {
        assertEquals(Position.offset1x1(), Position.create(1, 1))
    }

    @Test
    fun shouldProperlyReturnZero() {
        assertEquals(Position.zero(), Position.create(0, 0))
    }

    @Test
    fun shouldProperlyReturnDefaultPosition() {
        assertEquals(Position.defaultPosition(), Position.create(0, 0))
    }

    @Test
    fun shouldProperlyReturnUnknown() {
        assertEquals(
            Position.unknown(),
            Position.create(
                Int.MAX_VALUE, Int.MAX_VALUE
            )
        )
    }

    @Test
    fun shouldProperlyReturnTopLeftOf() {
        assertEquals(Position.topLeftOf(componentStub!!), POSITION_2X3)
    }

    @Test
    fun shouldProperlyReturnTopRightOf() {
        assertEquals(
            Position.topRightOf(componentStub!!),
            POSITION_2X3.withRelativeX(
                SIZE_3X4.width
            )
        )
    }

    @Test
    fun shouldProperlyReturnBottomLeftOf() {
        assertEquals(
            Position.bottomLeftOf(componentStub!!),
            POSITION_2X3.withRelativeY(
                SIZE_3X4.height
            )
        )
    }

    @Test
    fun shouldProperlyReturnBottomRightOf() {
        assertEquals(
            Position.bottomRightOf(componentStub!!),
            POSITION_2X3.plus(
                SIZE_3X4.toPosition()
            )
        )
    }

    @Test
    fun shouldProperlyCrate() {
        assertEquals(Position.create(2, 3), POSITION_2X3)
    }

    @Test
    fun shouldProperlyReturnDefault3DPosition() {
        assertEquals(Position3D.defaultPosition(), Position3D.defaultPosition())
    }

    @Test
    fun shouldProperlyCreate3DPosition() {
        assertEquals(Position3D.create(4, 3, 2), POSITION3D_4X3X2)
    }

    @Test
    fun shouldProperlyConvert2DTo3DPosition() {
        assertEquals(POSITION_2X3.toPosition3D(4), Position3D.create(2, 3, 4))
    }

    companion object {
        private val POSITION_2X3 = Position.create(2, 3)
        private val POSITION3D_4X3X2 = Position3D.create(4, 3, 2)
        private val SIZE_3X4 = Size.create(3, 4)
    }
}
