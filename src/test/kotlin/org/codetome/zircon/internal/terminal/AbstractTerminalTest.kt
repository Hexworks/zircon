package org.codetome.zircon.internal.terminal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.TerminalResizeListener
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class AbstractTerminalTest {

    lateinit var target: AbstractTerminal

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = VirtualTerminal(initialFont = FONT)
    }

    @Test
    fun shouldAddResizeListenerWhenAddIsCalled() {
        var resized = false
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        })
        target.setSize(Size(5, 5))
        assertThat(resized).isTrue()
    }

    @Test
    fun shouldNotResizeWhenSizeIsTheSame() {
        var resized = false
        target.setSize(Size(5, 5))
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        })
        target.setSize(Size(5, 5))
        assertThat(resized).isFalse()
    }

    @Test
    fun shouldRemoveListenerWhenRemoveisCalled() {
        var resized = false
        val listener = object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        }
        target.addResizeListener(listener)
        target.removeResizeListener(listener)
        target.setSize(Size(5, 5))
        assertThat(resized).isFalse()
    }

    companion object {
        val FONT = CP437TilesetResource.ROGUE_YUN_16X16.toFont()
    }

}