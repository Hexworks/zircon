package org.hexworks.zircon.internal.extensions

import org.assertj.core.api.Assertions
import org.junit.Test

class ListExtensionsKtTest {

    @Test
    fun shouldBeAbleToGetIfPresentWhenPresent() {
        val target = listOf(1)

        Assertions.assertThat(target[0]).isNotNull
    }

}
