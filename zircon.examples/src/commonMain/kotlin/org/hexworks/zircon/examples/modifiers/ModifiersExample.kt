package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.api.modifier.FadeIn
import org.hexworks.zircon.api.modifier.FadeInOut
import org.hexworks.zircon.api.modifier.FadeOut
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * Every built-in tile modifier on a random tile, with its name. The time-based ones restart
 * when the example is shown again.
 */
public val modifiersExample: Example = Example("Modifiers") { context ->
    val layer = context.contentLayer()
    val labelColor = ColorThemes.nord().accentColor

    fun redraw() {
        layer.clear()
        val random = Random(687483)
        // Bright colours only, and never the same twice: "hidden" keeps just the background.
        val colors = ANSIColor.entries.filter { it.name.startsWith("BRIGHT") }
        fun randomTile(): CharacterTile = characterTile {
            character = 'x'
            withStyleSet {
                val fg = colors[random.nextInt(colors.size)]
                val bg = (colors - fg)[random.nextInt(colors.size - 1)]
                foregroundColor = DefaultAnsiPalette[fg]
                backgroundColor = DefaultAnsiPalette[bg]
            }
        }
        val modifiers: List<Pair<String, () -> Modifier>> = listOf(
            "blink" to { SimpleModifiers.Blink },
            "hidden" to { SimpleModifiers.Hidden },
            "fade in" to { FadeIn() },
            "fade out" to { FadeOut() },
            "fade in+out" to { FadeInOut() },
            "delay 2s" to { Delay(2000L) },
        )
        modifiers.forEachIndexed { index, (name, modifier) ->
            val y = 1 + index * 2
            layer.drawText(name, Position(1, y), labelColor)
            layer.draw(randomTile().withAddedModifiers(modifier()), Position(14, y))
        }
    }
    ExampleView(context.emptyContent(), layers = listOf(layer), onShown = ::redraw)
}
