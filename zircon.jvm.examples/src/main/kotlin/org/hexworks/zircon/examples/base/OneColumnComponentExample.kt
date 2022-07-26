package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class OneColumnComponentExample : ComponentExample() {

    override fun addExamples(exampleArea: HBox) {
        buildVbox {
            name = "One Column Example Container"
            preferredSize = Size.create(exampleArea.width, exampleArea.height)
            componentRenderer = NoOpComponentRenderer()
            spacing = 1
        }.apply {
            build(this)
            exampleArea.addComponent(this)
        }
    }
}
