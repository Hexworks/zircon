package org.codetome.zircon.internal.modifier

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.modifier.SimpleModifiers
import org.junit.Test

class SimpleModifiersTest {

    @Test
    fun shouldReturnProperCacheKeyForObject() {
        assertThat(SimpleModifiers.Bold.generateCacheKey()).isEqualTo("SimpleModifiers:Bold")
    }

}
