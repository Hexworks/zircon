package org.hexworks.zircon.api.modifier

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.modifier.BorderPosition.BOTTOM
import org.hexworks.zircon.api.modifier.BorderPosition.RIGHT
import org.hexworks.zircon.api.modifier.BorderPosition.TOP
import org.hexworks.zircon.api.modifier.BorderType.DASHED
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = BorderBuilder.newBuilder().withBorderPositions(BOTTOM).build()
        val result: Border =
            border + BorderBuilder.newBuilder().withBorderType(DASHED).withBorderPositions(RIGHT).build()
        val expected: Border = BorderBuilder.newBuilder().withBorderPositions(BOTTOM, RIGHT).build()

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = BorderBuilder.newBuilder()
            .withBorderPositions(BOTTOM, TOP)
            .withBorderType(DASHED)
            .build().cacheKey
        assertThat(result).isEqualTo("Modifier.Border(t=DASHED,bp=[BOTTOM,TOP])")
    }
}
