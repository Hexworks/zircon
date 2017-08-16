package org.codetome.zircon.screen

import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.terminal.Size
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ScreenBufferTest {

    lateinit var target: ScreenBuffer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = ScreenBuffer(
                size = SIZE,
                filler = FILLER)
    }

    @Test
    fun test() {
        // this class is a no brainer, but coverage suffers if we don't do this...
        target.copyFrom(IMG, 1, 2, 1, 2, 1, 2)
        target.resize(Size(2, 2), FILLER)
    }

    companion object {
        val SIZE = Size(10, 10)
        val FILLER = TextCharacterBuilder.newBuilder().build()
        val IMG = TextImageBuilder.newBuilder()
                .filler(FILLER)
                .size(Size(5, 5))
                .build()
    }

}