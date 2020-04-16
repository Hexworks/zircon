package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.logArea
import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin

class CustomRendererExample : OneColumnComponentExampleKotlin() {

    override fun build(box: VBox) {

        val logArea = logArea()
                .withSize(box.size)
                .withDecorations(box())
                .build()

        val colored = label()
                .withSize(10, 1)
                .withRendererFunction { graphics, _ ->
                    graphics.draw("Colored".toCharacterTileString(StyleSet.create(RED, GREEN)))
                }.build()

        with(logArea) {
            addInlineText("This is a ")
            addInlineComponent(colored)
            addInlineText(" Label.")
            commitInlineElements()
        }

        val leftLabel = label()
                .withText("Nightmare")
                .withPosition(1, 0)
                .withColorTheme(ColorThemeBuilder.newBuilder()
                        .withAccentColor(BRIGHT_YELLOW)
                        .withPrimaryForegroundColor(RED)
                        .withPrimaryBackgroundColor(GREEN)
                        .withSecondaryForegroundColor(BLUE)
                        .withSecondaryBackgroundColor(CYAN)
                        .build())
                .build()

        logArea.addInlineComponent(leftLabel)
        logArea.commitInlineElements()

        box.addComponents(logArea)

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            CustomRendererExample().show("Custom Renderer")
        }
    }
}
