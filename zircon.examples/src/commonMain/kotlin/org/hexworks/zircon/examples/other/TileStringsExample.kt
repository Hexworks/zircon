package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.graphics.withStyleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A character tile string wrapped to a narrow box, with its own colours. */
public val tileStringsExample: Example = Example("Tile strings") { context ->
    val canvas = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    }
    val text = characterTileString {
        +"This is some text which is too long to fit on one line, so it wraps at the width of the string..."
        size = org.hexworks.zircon.api.data.Size(24, 6)
        textWrap = TextWrap.WRAP
        withStyleSet {
            foregroundColor = Color.fromString("#eeffee")
            backgroundColor = Color.fromString("#223344")
        }
    }
    canvas.draw(text, Position(2, 2))
    val clipped = characterTileString {
        +"This one has no wrapping and is clipped at the edge of its size instead."
        size = org.hexworks.zircon.api.data.Size(24, 1)
        textWrap = TextWrap.NO_WRAPPING
        withStyleSet {
            foregroundColor = Color.fromString("#223344")
            backgroundColor = Color.fromString("#eeffee")
        }
    }
    canvas.draw(clipped, Position(2, 10))
    ExampleView(context.emptyContent(), layers = listOf(canvas))
}
