package org.hexworks.zircon.api.component.theme

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.config.ZirconDefaults
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SystemThemeTest {

    private lateinit var original: ZirconDefaults

    @BeforeTest
    fun setUp() {
        original = ZirconDefaults.current
        SystemTheme.reset()
    }

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = original
        SystemTheme.reset()
    }

    @Test
    fun update_changes_the_mode_and_tells_the_listeners() {
        val seen = ArrayList<ThemeMode>()
        val subscription = SystemTheme.modeProperty.onChange { (_, mode) -> seen += mode }
        SystemTheme.update(mode = ThemeMode.LIGHT)
        SystemTheme.update(mode = ThemeMode.DARK)
        subscription.dispose()
        seen shouldBe listOf(ThemeMode.LIGHT, ThemeMode.DARK)
        SystemTheme.mode shouldBe ThemeMode.DARK
    }

    @Test
    fun update_keeps_what_it_is_not_told() {
        val terminal = Palettes.tokyoNight()
        SystemTheme.update(mode = ThemeMode.LIGHT)
        SystemTheme.update(palette = terminal)
        SystemTheme.mode shouldBe ThemeMode.LIGHT
        SystemTheme.palette shouldBe terminal
        SystemTheme.update(mode = ThemeMode.DARK)
        SystemTheme.palette shouldBe terminal
    }

    @Test
    fun update_refuses_system_as_a_mode() {
        shouldThrow<IllegalArgumentException> { SystemTheme.update(mode = ThemeMode.SYSTEM) }
    }

    @Test
    fun refresh_samples_the_detector() {
        ZirconDefaults.current = original.copy(systemThemeDetector = SystemThemeDetector.fixed(ThemeMode.LIGHT))
        SystemTheme.refresh()
        SystemTheme.mode shouldBe ThemeMode.LIGHT
        ZirconDefaults.current = original.copy(systemThemeDetector = SystemThemeDetector.NONE)
        SystemTheme.refresh()
        SystemTheme.mode shouldBe ThemeMode.DARK
    }

    @Test
    fun reset_forgets_the_palette() {
        SystemTheme.update(palette = Palettes.kanagawa())
        SystemTheme.reset()
        SystemTheme.palette shouldBe null
    }
}
