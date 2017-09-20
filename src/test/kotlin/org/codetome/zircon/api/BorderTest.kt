package org.codetome.zircon.api

import org.assertj.core.api.Assertions
import org.codetome.zircon.api.Modifiers.BorderFactory
import org.codetome.zircon.api.Modifiers.BorderPosition.*
import org.codetome.zircon.api.Modifiers.BorderType.DASHED
import org.codetome.zircon.api.Modifiers.BorderType.SOLID
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = BorderFactory.of(SOLID, BOTTOM)
        val result = border + BorderFactory.of(DASHED, RIGHT)
        val expected = BorderFactory.of(SOLID, BOTTOM, RIGHT)

        Assertions.assertThat(result).isEqualTo(expected)
    }
}