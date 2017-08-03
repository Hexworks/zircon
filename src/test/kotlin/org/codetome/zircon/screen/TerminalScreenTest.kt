package org.codetome.zircon.screen

import org.codetome.zircon.terminal.TerminalSize
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
        val SIZE = TerminalSize(10, 10)
        val TERMINAL = DefaultVirtualTerminal(SIZE)
    }
}