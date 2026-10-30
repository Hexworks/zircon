package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.ExampleContext
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** One column filling the content area, like the old `OneColumnComponentExample`. */
internal fun ExampleContext.oneColumn(build: (VBox) -> Unit): Container = vBox {
    // The builder has a contentSize of its own; the context's is meant.
    preferredSize = this@oneColumn.contentSize
    componentRenderer = NoOpComponentRenderer()
    spacing = 1
}.also(build)

/**
 * Two columns of half width, the left one plain and the right one on a boxed panel, like the
 * old `TwoColumnComponentExample`; [build] fills both.
 */
internal fun ExampleContext.twoColumns(build: (VBox) -> Unit): Container {
    val root = hBox {
        preferredSize = this@twoColumns.contentSize
        componentRenderer = NoOpComponentRenderer()
    }
    // One cell short of half: an hBox puts its spacing between the columns.
    val column = Size(contentSize.width / 2 - 1, contentSize.height)
    val left = vBox {
        preferredSize = column
        componentRenderer = NoOpComponentRenderer()
        spacing = 1
        decorations { +margin(1) }
    }
    val right = vBox {
        preferredSize = column
        spacing = 1
        decorations {
            +box(title = "Buttons on panel")
            +margin(1)
        }
    }
    build(left)
    build(right)
    root.addComponent(left)
    root.addComponent(right)
    return root
}

/** The old half-block decoration, 2.0 has only side characters. */
internal const val LEFT_HALF_BLOCK: Char = '▌'
internal const val RIGHT_HALF_BLOCK: Char = '▐'
