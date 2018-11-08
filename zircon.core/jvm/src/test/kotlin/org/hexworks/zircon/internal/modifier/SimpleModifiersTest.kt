package org.hexworks.zircon.internal.modifier

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.junit.Test

class SimpleModifiersTest {

    @Test
    fun shouldReturnProperCacheKeyForObject() {
        assertThat(SimpleModifiers.VerticalFlip.generateCacheKey()).isEqualTo("Modifier.VerticalFlip")
    }

}
