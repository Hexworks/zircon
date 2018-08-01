package org.codetome.zircon.internal.grid.application

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.builder.grid.DeviceConfigurationBuilder
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.grid.CursorStyle
import org.codetome.zircon.internal.component.impl.DefaultLabelTest
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.codetome.zircon.internal.grid.virtual.VirtualTileGrid
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean


class ApplicationTileGridTest {

    lateinit var target: ApplicationTileGrid
    lateinit var tileset: Tileset

    val fontTextureDraws = mutableListOf<Triple<TileTexture<*>, Int, Int>>()
    val cursorDraws = mutableListOf<Triple<Tile, Int, Int>>()
    var rendered = AtomicBoolean(false)

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = DefaultLabelTest.FONT.toFont()
        target = object : ApplicationTileGrid(
                deviceConfiguration = CONFIG,
                terminal = VirtualTileGrid(
                        initialSize = SIZE,
                        initialTileset = tileset)) {
            override fun drawFontTextureRegion(tileTexture: TileTexture<*>, x: Int, y: Int) {
                fontTextureDraws.add(Triple(tileTexture, x, y))
            }

            override fun drawCursor(character: Tile, x: Int, y: Int) {
                cursorDraws.add(Triple(character, x, y))
            }

            override fun getHeight() = SIZE.yLength * tileset.getHeight()

            override fun getWidth() = SIZE.xLength * tileset.getWidth()

            override fun doRender() {
                super.doRender()
                rendered.set(true)
            }
        }
    }


    @Ignore
    @Test
    fun shouldRenderAfterCreateIfCursorBlinksAndEnoughTimePassed() {
        target.doCreate()
        Thread.sleep(500)

        assertThat(rendered.get()).isTrue()
    }

    @Test
    fun shouldSendEofOnDispose() {
        val eofReceived = AtomicBoolean(false)
        EventBus.subscribe<Event.Input> {
            if (it.input == KeyStroke.EOF_STROKE) {
                eofReceived.set(true)
            }
        }

        target.doDispose()

        assertThat(eofReceived.get()).isTrue()
    }


    companion object {
        private const val BLINK_LEN_MS = 2L
        val SIZE = Size.create(10, 20)
        val CONFIG = DeviceConfigurationBuilder.newBuilder()
                .cursorBlinking(true)
                .blinkLengthInMilliSeconds(BLINK_LEN_MS)
                .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
                .build()
        val FONT = CP437TilesetResource.WANDERLUST_16X16
    }
}
