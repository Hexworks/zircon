package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class TwoColumnComponentExample : ComponentExample() {

    override fun addExamples(exampleArea: HBox) {
        val width = exampleArea.width / 2
        val height = exampleArea.height
        listOf(buildVbox {
            preferredSize = Size.create(width, height)
            componentRenderer = NoOpComponentRenderer()
            spacing = 1
            decoration = margin(1)
        }, buildVbox {
            preferredSize = Size.create(width, height)
            spacing = 1
            decorations = box(BoxType.SINGLE, "Buttons on panel") + margin(1)
        }).forEach { box ->
            build(box)
            exampleArea.addComponent(box)
        }
    }
}
