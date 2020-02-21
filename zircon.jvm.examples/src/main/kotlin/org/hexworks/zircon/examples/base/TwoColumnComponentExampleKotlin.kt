package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class TwoColumnComponentExampleKotlin(
        size: Size = Defaults.GRID_SIZE
) : ComponentExampleKotlin(size) {

    override fun addExamples(exampleArea: HBox) {
        val leftBox = vbox()
                .withSize(exampleArea.width / 2, exampleArea.height)
                .withComponentRenderer(NoOpComponentRenderer())
                .withSpacing(1)
                .build()
        val rightBox = vbox()
                .withDecorations(box(BoxType.SINGLE, "Buttons on panel"), shadow())
                .withSpacing(1)
                .withSize(exampleArea.width / 2, exampleArea.height)
                .build()
        rightBox.addComponent(label().build())

        build(leftBox)
        build(rightBox)

        exampleArea.addComponent(leftBox)
        exampleArea.addComponent(rightBox)
    }
}