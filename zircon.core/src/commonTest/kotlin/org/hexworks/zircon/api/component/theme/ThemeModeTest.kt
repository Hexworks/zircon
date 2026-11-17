package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.config.ZirconDefaults
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ThemeModeTest {

    private lateinit var original: ZirconDefaults

    @BeforeTest
    fun setUp() {
        original = ZirconDefaults.current
    }

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = original
        SystemTheme.reset()
    }

    @Test
    fun system_resolves_to_what_the_detector_reported_last() {
        ZirconDefaults.current = original.copy(systemThemeDetector = SystemThemeDetector.fixed(ThemeMode.LIGHT))
        SystemTheme.refresh()
        ThemeMode.SYSTEM.resolve() shouldBe ThemeMode.LIGHT
    }

    @Test
    fun system_resolves_to_what_a_host_pushed() {
        SystemTheme.update(mode = ThemeMode.LIGHT)
        ThemeMode.SYSTEM.resolve() shouldBe ThemeMode.LIGHT
    }

    @Test
    fun system_falls_back_to_dark_when_the_detector_does_not_know() {
        ZirconDefaults.current = original.copy(systemThemeDetector = SystemThemeDetector.NONE)
        SystemTheme.refresh()
        ThemeMode.SYSTEM.resolve() shouldBe ThemeMode.DARK
    }

    @Test
    fun light_and_dark_resolve_to_themselves() {
        ThemeMode.LIGHT.resolve() shouldBe ThemeMode.LIGHT
        ThemeMode.DARK.resolve() shouldBe ThemeMode.DARK
    }

    @Test
    fun the_platform_detector_never_answers_system() {
        val answer = platformSystemThemeDetector().detect()
        (answer == null || answer != ThemeMode.SYSTEM) shouldBe true
    }

    @Test
    fun system_answers_are_parsed_loosely() {
        "'prefer-dark'".toThemeModeOrNull() shouldBe ThemeMode.DARK
        "Dark".toThemeModeOrNull() shouldBe ThemeMode.DARK
        "'Adwaita-light'".toThemeModeOrNull() shouldBe ThemeMode.LIGHT
        "default".toThemeModeOrNull() shouldBe null
        null.toThemeModeOrNull() shouldBe null
    }
}
