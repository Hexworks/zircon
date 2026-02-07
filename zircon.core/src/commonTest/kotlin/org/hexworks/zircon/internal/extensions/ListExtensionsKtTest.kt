package org.hexworks.zircon.internal.extensions

import io.kotest.matchers.nulls.shouldNotBeNull
import kotlin.test.Test

class ListExtensionsKtTest {

    @Test
    fun shouldBeAbleToGetIfPresentWhenPresent() {
        val target = listOf(1)

        target[0].shouldNotBeNull()
    }
}
