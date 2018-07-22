package org.codetome.zircon.api.game

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Position3DTest {

    @Test
    fun shouldBeGreaterThanWhenZIsGreaterThanOther() {
        assertThat(POS.compareTo(POS_WITH_LESSER_Z))
                .isEqualTo(GREATER)
    }

    @Test
    fun shouldBeLessThanWhenZIsLessThanOther() {
        assertThat(POS.compareTo(POS_WITH_GREATER_Z))
                .isEqualTo(LESS)
    }

    @Test
    fun shouldBeGreaterThanWhenZIsEqualAndYIsGreaterThanOther() {
        assertThat(POS.compareTo(POS_WITH_EQUAL_Z_LESSER_Y))
                .isEqualTo(GREATER)
    }

    @Test
    fun shouldBeLessThanWhenZIsEqualAndYIsLessThanOther() {
        assertThat(POS.compareTo(POS_WITH_EQUAL_Z_GREATER_Y))
                .isEqualTo(LESS)
    }

    @Test
    fun shouldBeGreaterThanWhenZAndYIsEqualAndXIsGreaterThanOther() {
        assertThat(POS.compareTo(POS_WITH_EQUAL_Z_AND_Y_LESSER_X))
                .isEqualTo(GREATER)
    }

    @Test
    fun shouldBeLessThanWhenZAndYIsEqualAndXIsLessThanOther() {
        assertThat(POS.compareTo(POS_WITH_EQUAL_Z_AND_Y_GREATER_X))
                .isEqualTo(LESS)
    }

    @Test
    fun shouldBeEqualWhenEverythingIsEqual() {
        assertThat(POS.compareTo(POS))
                .isEqualTo(EQUAL)
    }

    companion object {

        val GREATER = 1
        val EQUAL = 0
        val LESS = -1

        val POS = Position3D.create(9, 9, 1)

        val POS_WITH_LESSER_Z = Position3D.create(8, 8, 0)
        val POS_WITH_GREATER_Z = Position3D.create(8, 8, 2)

        val POS_WITH_EQUAL_Z_LESSER_Y = Position3D.create(8, 8, 1)
        val POS_WITH_EQUAL_Z_GREATER_Y = Position3D.create(8, 10, 1)

        val POS_WITH_EQUAL_Z_AND_Y_LESSER_X = Position3D.create(8, 9, 1)
        val POS_WITH_EQUAL_Z_AND_Y_GREATER_X = Position3D.create(10, 9, 1)
    }
}
