package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Size
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultScrollableTest {

    lateinit var target: DefaultScrollable

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultScrollable(CURSOR_SPACE, VISIBLE_SPACE)
    }

    @Test
    fun test() {

    }

    companion object {
        val CURSOR_SPACE = Size.of(10, 5)
        val VISIBLE_SPACE = Size.of(2, 1)
    }

}