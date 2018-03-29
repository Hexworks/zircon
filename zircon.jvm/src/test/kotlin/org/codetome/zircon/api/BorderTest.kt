package org.codetome.zircon.api

import org.assertj.core.api.Assertions
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderPosition.BOTTOM
import org.codetome.zircon.api.modifier.BorderPosition.RIGHT
import org.codetome.zircon.api.modifier.BorderType
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = BorderBuilder.newBuilder().borderPositions(BOTTOM).build()
        val result = border + BorderBuilder.newBuilder().borderType(BorderType.DASHED).borderPositions(RIGHT).build()
        val expected = BorderBuilder.newBuilder().borderPositions(BOTTOM, RIGHT).build()

        Assertions.assertThat(result).isEqualTo(expected)
    }
}