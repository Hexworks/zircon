package org.codetome.zircon.internal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers.BorderPosition.BOTTOM
import org.codetome.zircon.api.Modifiers.BorderPosition.TOP
import org.codetome.zircon.api.Modifiers.BorderType.DASHED
import org.codetome.zircon.internal.BuiltInModifiers.BorderFactory
import org.junit.Test

class BuiltInModifiersTest {

    @Test
    fun shouldReturnProperCacheKeyForObject() {
        assertThat(BuiltInModifiers.Bold.generateCacheKey()).isEqualTo("Bold")
    }

    @Test
    fun shouldReturnProperCacheKeyForBorder() {
        val result = BorderFactory.create(DASHED, BOTTOM, TOP).generateCacheKey()
        assertThat(result).isEqualTo("BorderDASHEDBOTTOMTOP")
    }
}