package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ComponentStyleSet.Companion.newBuilder
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.StyleSet.Companion.create
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import java.util.*

class ComponentStyleSetExampleKotlin : OneColumnComponentExampleKotlin() {

    lateinit var button: Button

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val css = newBuilder()
                .withDefaultStyle(StyleSet.newBuilder().apply {
                    foregroundColor = CYAN
                    backgroundColor = BLACK
                }.build())
                .withActiveStyle(StyleSet.newBuilder().apply {
                    foregroundColor = CYAN
                    backgroundColor = BLACK
                }.build())
                .withFocusedStyle(StyleSet.newBuilder().apply {
                    foregroundColor = RED
                    backgroundColor = BLACK
                }.build())
                .build()

            ComponentStyleSetExampleKotlin().apply {
                show("Custom Component Style Set")
                button.componentStyleSet = css
            }
        }
    }

    override fun build(box: VBox) {
        val panel = panel()
            .withDecorations(box(title = "Buttons on panel"), shadow())
            .withPreferredSize(30, 20)
            .withPosition(5, 5)
            .build()
        box.addComponent(panel)

        button = button()
            .withText("Button")
            .withDecorations(side())
            .withPosition(1, 3)
            .build()
        panel.addComponent(button)

    }
}
