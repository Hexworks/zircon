package org.codetome.zircon.api.modifier

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.modifier.BorderPosition.*
import org.codetome.zircon.api.modifier.BorderType.*
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = BorderBuilder.newBuilder().borderPositions(BOTTOM).build()
        val result: Border = border + BorderBuilder.newBuilder().borderType(BorderType.DASHED).borderPositions(RIGHT).build()
        val expected: Border = BorderBuilder.newBuilder().borderPositions(BOTTOM, RIGHT).build()

        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = BorderBuilder.newBuilder()
                .borderPositions(BOTTOM, TOP)
                .borderType(DASHED)
                .build().generateCacheKey()
        assertThat(result).isEqualTo("Border:DASHED:BOTTOM:TOP")
    }
}
