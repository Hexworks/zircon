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
        val border = BorderBuilder.newBuilder().withBorderPositions(BOTTOM).build()
        val result: Border = border + BorderBuilder.newBuilder().withBorderType(BorderType.DASHED).withBorderPositions(RIGHT).build()
        val expected: Border = BorderBuilder.newBuilder().withBorderPositions(BOTTOM, RIGHT).build()

        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = BorderBuilder.newBuilder()
                .withBorderPositions(BOTTOM, TOP)
                .withBorderType(DASHED)
                .build().generateCacheKey()
        assertThat(result).isEqualTo("Modifier.Border(t=DASHED,bp=[BOTTOM,TOP])")
    }
}
