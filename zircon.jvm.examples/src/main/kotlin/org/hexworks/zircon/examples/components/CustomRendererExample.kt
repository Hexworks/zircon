package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.logArea
import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.examples.base.OneColumnComponentExample

object CustomRendererExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        CustomRendererExample.show("Custom Renderer")
    }

    override fun build(box: VBox) {

        val logArea = logArea()
            .withPreferredSize(box.size)
            .withDecorations(box())
            .build()

        val colored = label()
            .withPreferredSize(10, 1)
            .withComponentRenderer { graphics, _ ->
                graphics.draw(
                    "Colored".toCharacterTileString(
                        StyleSet.newBuilder()
                            .withBackgroundColor(RED)
                            .withForegroundColor(GREEN)
                            .build()
                    )
                )
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
            .withColorTheme(
                ColorThemeBuilder.newBuilder()
                    .withAccentColor(BRIGHT_YELLOW)
                    .withPrimaryForegroundColor(RED)
                    .withPrimaryBackgroundColor(GREEN)
                    .withSecondaryForegroundColor(BLUE)
                    .withSecondaryBackgroundColor(CYAN)
                    .build()
            )
            .build()

        logArea.addInlineComponent(leftLabel)
        logArea.commitInlineElements()

        box.addComponent(logArea)

    }
}
