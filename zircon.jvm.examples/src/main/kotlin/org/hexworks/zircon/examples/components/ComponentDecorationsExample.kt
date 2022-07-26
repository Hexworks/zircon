package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.checkBox
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.horizontalSlider
import org.hexworks.zircon.api.Components.progressBar
import org.hexworks.zircon.api.Components.radioButton
import org.hexworks.zircon.api.Components.radioButtonGroup
import org.hexworks.zircon.api.Components.toggleButton
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.OneColumnComponentExample

object ComponentDecorationsExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        ComponentDecorationsExample.show("Component Decorations Example")
    }

    override fun build(box: VBox) {
        val columns = hbox()
            .withPreferredSize(box.contentSize)
            .build()
        box.addComponent(columns)
        val half = columns.contentSize.width / 2
        val interactive = vbox()
            .withPreferredSize(half, columns.height)
            .withSpacing(1)
            .withDecorations(box(BoxType.SINGLE, "Interactive"))
            .build()
        val nonInteractive = vbox()
            .withPreferredSize(half, columns.height)
            .withSpacing(1)
            .withDecorations(box(BoxType.SINGLE, "Non-Interactive"))
            .build()
        columns.addComponents(interactive, nonInteractive)
        addComponents(interactive, ComponentDecorationRenderer.RenderingMode.INTERACTIVE)
        addComponents(nonInteractive, ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE)
    }

    fun addComponents(box: VBox, renderingMode: ComponentDecorationRenderer.RenderingMode?) {
        box.addComponent(
            button()
                .withText("Click Me!")
                .withDecorations(halfBlock(renderingMode!!))
                .build()
        )
        box.addComponent(
            checkBox()
                .withText("Check Me!")
                .withDecorations(box(BoxType.SINGLE, "", renderingMode))
                .build()
        )
        box.addComponent(
            toggleButton()
                .withText("Toggle Me!")
                .withDecorations(box(BoxType.BASIC, "", renderingMode))
                .build()
        )
        val a = radioButton()
            .withText("Press A!")
            .withDecorations(box(BoxType.DOUBLE, "", renderingMode))
            .withKey("a")
            .build()
        box.addComponent(a)
        val b = radioButton()
            .withText("Press B!")
            .withDecorations(box(BoxType.DOUBLE, "", renderingMode))
            .withKey("b")
            .build()
        box.addComponent(b)
        val rbg = radioButtonGroup().build()
        rbg.addComponents(a, b)
        box.addComponent(
            horizontalSlider()
                .withMinValue(1)
                .withMaxValue(100)
                .withNumberOfSteps(box.contentSize.width - 5)
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Slide Me", renderingMode))
                .build()
        )
        val leftBar = progressBar()
            .withDisplayPercentValueOfProgress(true)
            .withNumberOfSteps(box.contentSize.width - 4)
            .withRange(100)
            .withDecorations(
                box(
                    BoxType.TOP_BOTTOM_DOUBLE, "I'm progressing",
                    renderingMode
                )
            )
            .build()
        leftBar.progress = 42.0
        box.addComponent(leftBar)
    }
}