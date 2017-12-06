package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.animation.AnimationHandler
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.graphics.DefaultAnimation
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AnimationHandlerTest {

    lateinit var target: AnimationHandler

    @Mock
    lateinit var screenMock: Screen

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = AnimationHandler(screenMock)
    }

    @Test(expected = IllegalStateException::class)
    fun shouldCloseProperlyWhenClosed() {
        target.close()

        target.addAnimation(DefaultAnimation(listOf(), 1L, 1, 1, 1))
    }
}