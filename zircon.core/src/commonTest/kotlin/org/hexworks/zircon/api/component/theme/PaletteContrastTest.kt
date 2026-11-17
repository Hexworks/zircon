package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.PaletteStyles.hovered
import org.hexworks.zircon.api.component.theme.PaletteStyles.pressed
import org.hexworks.zircon.internal.component.theme.contrast
import kotlin.test.Test

/**
 * Floors for the color pairs the default theme draws, over every bundled palette. The floors
 * are the weakest bundled member (Tokyo Night Day, Solarized) rounded down, so this guards
 * the derivations (a hover drawn in muted text on the primary once read at 1:1) rather than
 * enforcing WCAG.
 */
class PaletteContrastTest {

    private fun check(
        floor: Double,
        name: String,
        except: (ComponentPalette) -> Boolean = { false },
        pair: (ComponentPalette) -> Pair<Color, Color>,
    ) {
        val failures = Palettes.all().filterNot(except).mapNotNull { palette ->
            val (fg, bg) = pair(palette)
            val ratio = contrast(fg, bg)
            if (ratio < floor) "${palette.name}: $name is ${(ratio * 10).toInt() / 10.0}:1, floor $floor" else null
        }
        failures shouldBe emptyList()
    }

    @Test
    fun body_text_reads_on_every_surface() {
        check(4.0, "onBackground / background") { it.onBackground to it.background }
        check(3.5, "onSurface / surface") { it.onSurface to it.surface }
        check(2.8, "onSurfaceRaised / surfaceRaised") { it.onSurfaceRaised to it.surfaceRaised }
        check(3.8, "onBackground / surfaceSunken") { it.onBackground to it.surfaceSunken }
        check(2.0, "textMuted / surface") { it.textMuted to it.surface }
    }

    @Test
    fun every_filled_state_of_a_button_reads() {
        listOf(Variant.DEFAULT, Variant.SECONDARY, Variant.INFO, Variant.SUCCESS, Variant.WARNING, Variant.ERROR).forEach { variant ->
            check(2.7, "$variant focused") { p -> p.emphasisColors(variant).let { (c, on) -> on to c } }
            check(2.0, "$variant hovered") { p -> p.emphasisColors(variant).let { (c, on) -> on to p.hovered(c) } }
            check(2.8, "$variant pressed") { p -> p.emphasisColors(variant).let { (c, on) -> on to pressed(c, on) } }
        }
    }

    @Test
    fun the_selection_and_the_fields_read() {
        check(3.0, "onSelection / selection") { it.onSelection to it.selection }
        // Neobrutalism's primary is a fill (ink is drawn on it); the default theme shows it as text, faintly.
        check(2.4, "primary / surface", except = { it.name.startsWith("Neobrutalism") }) { it.primary to it.surface }
    }
}
