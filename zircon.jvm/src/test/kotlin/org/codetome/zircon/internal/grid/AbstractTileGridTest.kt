package org.codetome.zircon.internal.grid

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.grid.GridResizeListener
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.codetome.zircon.internal.grid.virtual.VirtualTileGrid
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class AbstractTileGridTest {

    lateinit var target: AbstractTileGrid
    lateinit var tileset: Tileset

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = DefaultLabelTest.FONT.toFont()
        MockitoAnnotations.initMocks(this)
        target = VirtualTileGrid(initialTileset = tileset)
    }

    @Test
    fun shouldAddResizeListenerWhenAddIsCalled() {
        var resized = false
        target.addResizeListener(object : GridResizeListener {
            override fun onResized(tileGrid: TileGrid, newSize: Size) {
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
        target.addResizeListener(object : GridResizeListener {
            override fun onResized(tileGrid: TileGrid, newSize: Size) {
                resized = true
            }
        })
        target.setSize(Size.create(5, 5))
        assertThat(resized).isFalse()
    }

    @Test
    fun shouldRemoveListenerWhenRemoveisCalled() {
        var resized = false
        val listener = object : GridResizeListener {
            override fun onResized(tileGrid: TileGrid, newSize: Size) {
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
