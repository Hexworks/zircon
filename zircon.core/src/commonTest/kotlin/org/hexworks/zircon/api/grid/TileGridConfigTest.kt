package org.hexworks.zircon.api.grid

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.grid.DEFAULT_BLINK_LENGTH
import org.hexworks.zircon.api.builder.grid.DEFAULT_IS_CURSOR_BLINKING
import org.hexworks.zircon.api.builder.grid.DEFAULT_SIZE
import org.hexworks.zircon.api.builder.grid.DEFAULT_TILESET
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.builder.grid.tileGridConfig
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test
import kotlin.test.assertEquals

class TileGridConfigTest {

    @Test
    fun when_built_with_defaults_then_it_should_be_built_properly() {
        assertEquals(
            TileGridConfig(
                size = DEFAULT_SIZE,
                tileset = DEFAULT_TILESET,
                blinkLengthInMilliSeconds = DEFAULT_BLINK_LENGTH,
                isCursorBlinking = DEFAULT_IS_CURSOR_BLINKING,
            ),
            tileGridConfig()
        )
    }

    @Test
    fun when_a_grid_is_built_then_it_carries_the_config_and_does_not_touch_the_defaults() {
        val before = ZirconDefaults.current
        val tileset = CP437TilesetResources.aduDhabi16x16()

        val grid = tileGrid {
            size = Size(10, 5)
            this.tileset = tileset
        }

        assertEquals(Size(10, 5), grid.size)
        assertEquals(tileset, grid.tileset)
        assertEquals(before, ZirconDefaults.current)
    }
}
