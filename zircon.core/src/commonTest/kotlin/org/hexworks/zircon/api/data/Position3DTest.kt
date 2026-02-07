package org.hexworks.zircon.api.data

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Position3DTest {

    @Test
    fun shouldBeGreaterThanWhenZIsGreaterThanOther() {
        POS.compareTo(POS_WITH_LESSER_Z) shouldBe GREATER
    }

    @Test
    fun shouldBeLessThanWhenZIsLessThanOther() {
        POS.compareTo(POS_WITH_GREATER_Z) shouldBe LESS
    }

    @Test
    fun shouldBeGreaterThanWhenZIsEqualAndYIsGreaterThanOther() {
        POS.compareTo(POS_WITH_EQUAL_Z_LESSER_Y) shouldBe GREATER
    }

    @Test
    fun shouldBeLessThanWhenZIsEqualAndYIsLessThanOther() {
        POS.compareTo(POS_WITH_EQUAL_Z_GREATER_Y) shouldBe LESS
    }

    @Test
    fun shouldBeGreaterThanWhenZAndYIsEqualAndXIsGreaterThanOther() {
        POS.compareTo(POS_WITH_EQUAL_Z_AND_Y_LESSER_X) shouldBe GREATER
    }

    @Test
    fun shouldBeLessThanWhenZAndYIsEqualAndXIsLessThanOther() {
        POS.compareTo(POS_WITH_EQUAL_Z_AND_Y_GREATER_X) shouldBe LESS
    }

    @Test
    fun shouldBeEqualWhenEverythingIsEqual() {
        POS.compareTo(POS) shouldBe EQUAL
    }

    companion object {

        const val GREATER = 1
        const val EQUAL = 0
        const val LESS = -1

        val POS = Position3D.create(9, 9, 1)

        val POS_WITH_LESSER_Z = Position3D.create(8, 8, 0)
        val POS_WITH_GREATER_Z = Position3D.create(8, 8, 2)

        val POS_WITH_EQUAL_Z_LESSER_Y = Position3D.create(8, 8, 1)
        val POS_WITH_EQUAL_Z_GREATER_Y = Position3D.create(8, 10, 1)

        val POS_WITH_EQUAL_Z_AND_Y_LESSER_X = Position3D.create(8, 9, 1)
        val POS_WITH_EQUAL_Z_AND_Y_GREATER_X = Position3D.create(10, 9, 1)
    }
}
