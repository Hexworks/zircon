package org.hexworks.zircon.api.config

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.config.DEFAULT_COLOR_THEME
import org.hexworks.zircon.api.builder.config.DEFAULT_DEBUG_MODE
import org.hexworks.zircon.api.builder.config.DEFAULT_DEFAULT_TILESET
import org.hexworks.zircon.api.builder.config.ZirconDefaultsBuilder
import org.hexworks.zircon.api.builder.config.zirconDefaults
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.config.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.config.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ZirconDefaultsTest {

    private val original = ZirconDefaults.current

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = original
    }

    @Test
    fun when_built_with_defaults_then_it_should_be_built_properly() {
        assertEquals(
            ZirconDefaults(
                tileset = DEFAULT_DEFAULT_TILESET,
                theme = DEFAULT_COLOR_THEME,
                debugMode = DEFAULT_DEBUG_MODE,
                debugConfig = DEFAULT_DEBUG_CONFIG,
                shortcutsConfig = DEFAULT_SHORTCUTS_CONFIG,
            ),
            ZirconDefaultsBuilder().build()
        )
    }

    @Test
    fun when_installed_then_builders_pick_the_new_defaults_up() {
        val installed = zirconDefaults {
            tileset = CP437TilesetResources.aduDhabi16x16()
            theme = ColorThemes.arc()
            debugMode = true
            debug { relaxBoundsCheck = true }
        }

        assertEquals(installed, ZirconDefaults.current)
        assertEquals(ColorThemes.arc(), ZirconDefaults.current.theme)
        assertEquals(CP437TilesetResources.aduDhabi16x16(), tileGraphics { }.tileset)
    }

    @Test
    fun bounds_check_is_only_relaxed_in_debug_mode() {
        assertTrue(ZirconDefaultsBuilder().apply { debug { relaxBoundsCheck = true } }.build().shouldCheckBounds())
        assertFalse(ZirconDefaultsBuilder().apply {
            debugMode = true
            debug { relaxBoundsCheck = true }
        }.build().shouldCheckBounds())
    }
}
