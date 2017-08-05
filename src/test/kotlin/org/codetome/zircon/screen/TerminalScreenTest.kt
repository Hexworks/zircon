package org.codetome.zircon.screen

import org.codetome.zircon.terminal.Size
import org.codetome.zircon.terminal.virtual.DefaultVirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class TerminalScreenTest {

    lateinit var target: TerminalScreen

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = TerminalScreen(TERMINAL)
    }

    @Test
    fun test() {

    }


    companion object {
        val SIZE = Size(10, 10)
        val TERMINAL = DefaultVirtualTerminal(SIZE)
    }
}