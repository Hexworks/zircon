package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.DebugConfig.Companion.DEFAULT_DEBUG_CONFIG
import org.hexworks.zircon.api.application.ShortcutsConfig.Companion.DEFAULT_SHORTCUTS_CONFIG
import org.hexworks.zircon.api.builder.application.*
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.renderer.virtual.VirtualTileset
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
    fun When_built_with_defaults_Then_it_should_be_built_properly() {
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
                iconData = null,
                iconPath = null,
                debugConfig = DEFAULT_DEBUG_CONFIG,
                shortcutsConfig = DEFAULT_SHORTCUTS_CONFIG,
                tilesetFactories = mapOf(),
                textureModifierStrategies = mapOf(),
                customProperties = mapOf(),
            ), actual
        )
    }

    @Test
    fun When_built_with_params_Then_it_should_be_built_properly() {
        val blinkMs = 50L
        val isBlinking = !DEFAULT_IS_CURSOR_BLINKING
        val clipboard = !DEFAULT_CLIPBOARD_AVAILABLE
        val tileset = CP437TilesetResources.anikki16x16()
        val theme = ColorThemes.arc()
        val debug = !DEFAULT_DEBUG_MODE
        val gridSize = size {
            width = 40
            height = 30
        }
        val isFullScreen = !DEFAULT_FULL_SCREEN
        val appTitle = "Hello"
        val icon = "/icon.ico"

        val actual = appConfig {
            blinkLengthInMilliSeconds = blinkMs
            isCursorBlinking = isBlinking
            isClipboardAvailable = clipboard
            defaultTileset = tileset
            defaultColorTheme = theme
            debugMode = debug
            size = gridSize
            fullScreen = isFullScreen
            title = appTitle
            iconPath = icon

            debugConfig {
                displayGrid = true
                displayCoordinates = true
                relaxBoundsCheck = true
            }

            shortcutsConfig {
                activateFocused = KeyboardEventMatcher(
                    type = KeyboardEventType.KEY_PRESSED,
                    code = KeyCode.ADD
                )
                deactivateActivated = KeyboardEventMatcher(
                    type = KeyboardEventType.KEY_RELEASED,
                    code = KeyCode.ADD
                )
                focusNext = KeyboardEventMatcher(
                    type = KeyboardEventType.KEY_PRESSED,
                    code = KeyCode.SPACE,
                    shiftDown = false
                )
                focusPrevious = KeyboardEventMatcher(
                    type = KeyboardEventType.KEY_PRESSED,
                    code = KeyCode.SPACE,
                    shiftDown = true
                )
            }

            customProperty<String> {
                key = UserNameKey
                value = "Jon"
            }

            tilesetFactory {
                targetType = Char::class
                tileType = TileType.CHARACTER_TILE
                tilesetType = TilesetType.TrueTypeFont
                factoryFunction { _ -> VirtualTileset() }
            }

            textureModifier {
                modifierType = Unknown::class
                targetType = Char::class
                transformer { context, _ ->
                    context.copy(
                        texture = '?',
                        context = "?"
                    )
                }
            }

        }

        // TODO: test it
    }

    @Test
    fun When_a_property_is_overwritten_Then_it_is_properly_reflected_in_the_end_result() {

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
    fun When_multiple_properties_are_set_Then_they_are_all_present() {
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
