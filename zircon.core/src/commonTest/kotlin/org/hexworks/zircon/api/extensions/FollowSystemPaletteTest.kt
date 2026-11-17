package org.hexworks.zircon.api.extensions

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.component.theme.SystemTheme
import org.hexworks.zircon.api.component.theme.SystemThemeDetector
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Size
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class FollowSystemPaletteTest {

    private lateinit var original: ZirconDefaults

    private fun screen() = tileGrid { size = Size(10, 5) }.toScreen()

    private fun family(mode: ThemeMode) = Palettes.catppuccin(mode)

    @BeforeTest
    fun setUp() {
        original = ZirconDefaults.current
        ZirconDefaults.current = original.copy(systemThemeDetector = SystemThemeDetector.fixed(ThemeMode.DARK))
        SystemTheme.reset()
    }

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = original
        SystemTheme.reset()
    }

    @Test
    fun theHostPaletteWinsOverTheFallback() {
        val screen = screen()
        val terminal = Palettes.tokyoNight()
        SystemTheme.update(palette = terminal)
        screen.followSystemPalette(::family)
        screen.componentPalette shouldBe terminal
    }

    @Test
    fun withoutAHostPaletteTheFallbackFollowsTheMode() {
        val screen = screen()
        screen.followSystemPalette(::family)
        screen.componentPalette.name shouldBe Palettes.catppuccin(ThemeMode.DARK).name
        SystemTheme.update(mode = ThemeMode.LIGHT)
        screen.componentPalette.name shouldBe Palettes.catppuccin(ThemeMode.LIGHT).name
        SystemTheme.update(palette = Palettes.kanagawa())
        screen.componentPalette.name shouldBe Palettes.kanagawa().name
    }

    @Test
    fun anExplicitPaletteEndsTheFollow() {
        val screen = screen()
        val follow = screen.followSystemPalette(::family)
        val pinned = Palettes.kanagawa()
        screen.componentPalette = pinned
        follow.disposed shouldBe true
        SystemTheme.update(mode = ThemeMode.LIGHT)
        screen.componentPalette shouldBe pinned
    }

    @Test
    fun closingTheScreenEndsTheFollow() {
        val screen = screen()
        val follow = screen.followSystemPalette(::family)
        screen.close()
        follow.disposed shouldBe true
    }

    @Test
    fun disposingTheSubscriptionEndsTheFollow() {
        val screen = screen()
        val follow = screen.followSystemPalette(::family)
        follow.dispose()
        val before = screen.componentPalette
        SystemTheme.update(mode = ThemeMode.LIGHT)
        screen.componentPalette shouldBe before
    }

    @Test
    fun theDefaultsMakeANewScreenFollow() {
        ZirconDefaults.current = ZirconDefaults.current.copy(followSystemPalette = ::family)
        val screen = screen()
        screen.componentPalette.name shouldBe Palettes.catppuccin(ThemeMode.DARK).name
        SystemTheme.update(mode = ThemeMode.LIGHT)
        screen.componentPalette.name shouldBe Palettes.catppuccin(ThemeMode.LIGHT).name
    }
}
