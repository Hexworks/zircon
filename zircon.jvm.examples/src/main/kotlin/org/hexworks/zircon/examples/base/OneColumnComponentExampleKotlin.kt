package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class OneColumnComponentExampleKotlin(
        size: Size = GRID_SIZE
) : ComponentExampleKotlin(size) {

    override fun addExamples(exampleArea: HBox) {
        val box = vbox()
                .withSize(exampleArea.width, exampleArea.height)
                .withComponentRenderer(NoOpComponentRenderer())
                .withSpacing(1)
                .build()
        build(box)
        exampleArea.addComponent(box)
    }
}