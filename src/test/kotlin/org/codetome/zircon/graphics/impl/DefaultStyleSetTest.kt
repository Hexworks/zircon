package org.codetome.zircon.graphics.impl

import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DefaultStyleSetTest {

    lateinit var target: DefaultStyleSet

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = DefaultStyleSet()
    }

    @Test
    fun test() {

    }


}