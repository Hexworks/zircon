package org.codetome.zircon.internal.extensions

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ListExtensionsKtTest {

    @Test
    fun shouldBeAbleToGetIfPresentWhenPresent() {
        val target = listOf(1)

        Assertions.assertThat(target.getIfPresent(0).isPresent).isTrue()
    }

    @Test
    fun shouldNotBeAbleToGetIfPresentWhenNotPresent() {
        val target = listOf(1)

        Assertions.assertThat(target.getIfPresent(1).isPresent).isFalse()
    }
}
