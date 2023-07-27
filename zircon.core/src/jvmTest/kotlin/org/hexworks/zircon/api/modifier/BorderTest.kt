package org.hexworks.zircon.api.modifier

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.modifier.border
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.modifier.BorderType.DASHED
import org.junit.Test

class BorderTest {

    @Test
    fun addingTwoBordersShouldCombineTheirBorderPositions() {
        val border = border {
            borderPositions = setOf(BOTTOM)
        }
        val result: Border =
            border + border {
                borderType = DASHED
                borderPositions = setOf(RIGHT)
            }
        val expected: Border = border {
            borderPositions = setOf(BOTTOM, RIGHT)
        }

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = border {
            borderPositions = setOf(BOTTOM, TOP)
            borderType = DASHED
        }.cacheKey
        assertThat(result).isEqualTo("Modifier.Border(t=DASHED,bp=[BOTTOM,TOP])")
    }
}
