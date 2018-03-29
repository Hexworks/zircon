package org.codetome.zircon.internal

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SimpleModifiersTest {

    @Test
    fun shouldReturnProperCacheKeyForObject() {
        assertThat(SimpleModifiers.Bold.generateCacheKey()).isEqualTo("Bold")
    }

}