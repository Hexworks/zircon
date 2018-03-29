package org.codetome.zircon.api.modifier

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.modifier.BorderPosition.*
import org.codetome.zircon.api.modifier.BorderType.*
import org.junit.Test

class BorderTest {


    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = BorderBuilder.newBuilder()
                .borderPositions(BOTTOM, TOP)
                .borderType(DASHED)
                .build().generateCacheKey()
        assertThat(result).isEqualTo("BorderDASHEDBOTTOMTOP")
    }
}