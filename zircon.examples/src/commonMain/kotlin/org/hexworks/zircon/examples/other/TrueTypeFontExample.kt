package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.graphics.withStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Text drawn with the bundled TrueType fonts, one layer per font. All six are the square
 * variants, so each glyph fits the grid's cell; the fonts still differ in how much of the
 * cell their glyphs fill, which is what the font pack's originals look like.
 */
public val trueTypeFontExample: Example = Example("TrueType") { context ->
    val theme = context.screen.theme
    val fonts = listOf(
        "IBM BIOS" to TrueTypeFontResources.ibmBios(16),
        "Amstrad" to TrueTypeFontResources.amstrad(16),
        "Tandy" to TrueTypeFontResources.tandy(16),
        "Kaypro" to TrueTypeFontResources.kaypro(16),
        "VTech" to TrueTypeFontResources.vtech(16),
        "AT&T" to TrueTypeFontResources.att(16),
    )
    val lineSize = Size(context.contentSize.width - 4, 2)
    val layers = fonts.mapIndexed { index, (name, font) ->
        layer {
            size = lineSize
            offset = Position(EXAMPLE_TAB_WIDTH + 2, EXAMPLE_TOP + 2 + index * 3)
            tileset = font
        }.apply {
            draw(characterTileString {
                text = "$name: this is written with a true type font..."
                size = lineSize
                withStyleSet {
                    foregroundColor = theme.primaryForegroundColor
                    backgroundColor = theme.primaryBackgroundColor
                }
            })
        }
    }
    ExampleView(context.emptyContent(), layers = layers)
}
