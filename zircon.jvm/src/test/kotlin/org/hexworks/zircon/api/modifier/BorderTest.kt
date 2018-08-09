package org.hexworks.zircon.api.modifier

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.modifier.BorderType.*
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
        assertThat(result).isEqualTo("Border(t=DASHED,bp=[BOTTOM,TOP])")
    }
}
