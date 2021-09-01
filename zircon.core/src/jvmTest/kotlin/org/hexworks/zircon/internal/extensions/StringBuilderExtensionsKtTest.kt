package org.hexworks.zircon.internal.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.lang.StringBuilder

class StringBuilderExtensionsKtTest {

    val target = StringBuilder(STRING)

    @Test
    fun shouldBePresentWhenGetIfPresentIsCalledWithValidIdx() {
        assertThat(target.getOrNull(STRING.length - 1)).isNotNull
    }

    @Test
    fun shouldNotBePresentWhenGetIfPresentIsCalledWithInvalidIdx() {
        assertThat(target.getOrNull(STRING.length)).isNull()
    }

    companion object {
        val STRING = "STRING"
    }
}
