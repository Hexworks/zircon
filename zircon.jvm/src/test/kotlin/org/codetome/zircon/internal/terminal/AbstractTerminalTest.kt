package org.codetome.zircon.internal.terminal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.TerminalResizeListener
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.codetome.zircon.internal.terminal.virtual.VirtualTerminal
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class AbstractTerminalTest {

    lateinit var target: AbstractTerminal
    lateinit var tileset: Tileset

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = DefaultLabelTest.FONT.toFont()
        MockitoAnnotations.initMocks(this)
        target = VirtualTerminal(initialTileset = tileset)
    }

    @Test
    fun shouldAddResizeListenerWhenAddIsCalled() {
        var resized = false
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        })
        target.setSize(Size.create(5, 5))
        assertThat(resized).isTrue()
    }

    @Test
    fun shouldNotResizeWhenSizeIsTheSame() {
        var resized = false
        target.setSize(Size.create(5, 5))
        target.addResizeListener(object : TerminalResizeListener {
            override fun onResized(terminal: Terminal, newSize: Size) {
                resized = true
            }
        })
        target.setSize(Size.create(5, 5))
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
        target.setSize(Size.create(5, 5))
        assertThat(resized).isFalse()
    }

    companion object {
        val FONT = CP437TilesetResource.ROGUE_YUN_16X16
    }

}
