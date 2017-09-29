package org.codetome.zircon.api

import org.assertj.core.api.Assertions
import org.codetome.zircon.api.Modifiers.BorderPosition.BOTTOM
import org.codetome.zircon.api.Modifiers.BorderPosition.RIGHT
import org.codetome.zircon.api.Modifiers.BorderType.SOLID
import org.codetome.zircon.internal.BuiltInModifiers.BorderFactory.create
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = create(SOLID, BOTTOM)
        val result = border + create(Modifiers.BorderType.DASHED, RIGHT)
        val expected = create(SOLID, BOTTOM, RIGHT)

        Assertions.assertThat(result).isEqualTo(expected)
    }
}