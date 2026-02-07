package org.hexworks.zircon.internal.extensions

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlin.test.Test

class StringBuilderExtensionsKtTest {

    val target = StringBuilder(STRING)

    @Test
    fun shouldBePresentWhenGetIfPresentIsCalledWithValidIdx() {
        target.getOrNull(STRING.length - 1).shouldNotBeNull()
    }

    @Test
    fun shouldNotBePresentWhenGetIfPresentIsCalledWithInvalidIdx() {
        target.getOrNull(STRING.length).shouldBeNull()
    }

    companion object {
        const val STRING = "STRING"
    }
}
