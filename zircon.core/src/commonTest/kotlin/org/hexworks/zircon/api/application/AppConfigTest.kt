package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.application.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.application.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import org.hexworks.zircon.api.builder.application.*
import org.hexworks.zircon.api.builder.application.TilesetFactoryBuilder.Companion.DEFAULT_TILESET_FACTORIES
import org.hexworks.zircon.api.modifier.TextureModifier
import kotlin.test.Test
import kotlin.test.assertEquals

private object TestAppConfigKey : AppConfigKey<String>
private object TestAppConfigKey2 : AppConfigKey<String>

object Unknown : TextureModifier {
    override val cacheKey: String
        get() = "?"
}

object UserNameKey : AppConfigKey<String>

class AppConfigTest {

    @Test
    fun when_built_with_defaults_then_it_should_be_built_properly() {
        val actual = appConfig()

        assertEquals(
            AppConfig(
                blinkLengthInMilliSeconds = DEFAULT_BLINK_LENGTH,
                isCursorBlinking = DEFAULT_IS_CURSOR_BLINKING,
                isClipboardAvailable = DEFAULT_CLIPBOARD_AVAILABLE,
                defaultTileset = DEFAULT_TILESET,
                defaultGraphicalTileset = DEFAULT_GRAPHICAL_TILESET,
                defaultColorTheme = DEFAULT_COLOR_THEME,
                debugMode = DEFAULT_DEBUG_MODE,
                size = DEFAULT_SIZE,
                fullScreen = DEFAULT_FULL_SCREEN,
                title = DEFAULT_TITLE,
                iconPath = null,
                debugConfig = DEFAULT_DEBUG_CONFIG,
                shortcutsConfig = DEFAULT_SHORTCUTS_CONFIG,
                tilesetFactories = DEFAULT_TILESET_FACTORIES,
                textureModifierStrategies = mapOf(),
                customProperties = mapOf(),
            ), actual
        )
    }

    @Test
    fun when_a_property_is_overwritten_then_it_is_properly_reflected_in_the_end_result() {

        val result = appConfig {
            customProperty<String> {
                key = TestAppConfigKey
                value = "foo"
            }
            customProperty<String> {
                key = TestAppConfigKey
                value = "bar"
            }
        }

        assertEquals("bar", result.getOrNull(TestAppConfigKey))
    }

    @Test
    fun when_multiple_properties_are_set_then_they_are_all_present() {
        val result = appConfig {
            customProperty<String> {
                key = TestAppConfigKey
                value = "foo"
            }
            customProperty<String> {
                key = TestAppConfigKey2
                value = "bar"
            }
        }

        assertEquals("foo", result.getOrNull(TestAppConfigKey))
        assertEquals("bar", result.getOrNull(TestAppConfigKey2))
    }
}
