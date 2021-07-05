package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.ComponentStyleSet.Companion.newBuilder
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.StyleSet.Companion.create
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import java.util.*

class ComponentStyleSetExampleKotlin : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ComponentStyleSetExampleKotlin().show("Moving a Component")
        }
    }

    override fun build(box: VBox) {
        val panel = panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withPreferredSize(30, 20)
                .withPosition(5, 5)
                .build()
        box.addComponent(panel)
        val compStyleSet = newBuilder()
                .withDefaultStyle(create(CYAN, BLACK, HashSet()))
                .withActiveStyle(create(CYAN, BLACK, HashSet()))
                .withFocusedStyle(create(RED, BLACK, HashSet()))
                .build()
        val simpleBtn = button()
                .withText("Button")
                .withDecorations(side())
                .withComponentStyleSet(compStyleSet)
                .withPosition(1, 3)
                .build()
        panel.addComponent(simpleBtn)
    }
}
