package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class OneColumnComponentExampleKotlin(
    size: Size = GRID_SIZE
) : ComponentExampleKotlin(size) {

    override fun addExamples(exampleArea: HBox) {
        val box = VBoxBuilder().apply {
            name = "One Column Example Container"
            preferredSize = Size.create(exampleArea.width, exampleArea.height)
            componentRenderer = NoOpComponentRenderer()
            spacing = 1
        }.build()
        build(box)
        exampleArea.addComponent(box)
    }
}
