package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.extensions.addComponents
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Toggle buttons with every decoration, one that hides itself when clicked and a disabled one. */
public val toggleButtonsExample: Example = Example("Toggles") { context ->
    val columnSize = Size(context.contentSize.width / 2, context.contentSize.height)
    val root = hBox { preferredSize = context.contentSize; componentRenderer = NoOpComponentRenderer() }
    val left = vBox { preferredSize = columnSize; componentRenderer = NoOpComponentRenderer(); spacing = 1 }
    val right = vBox {
        preferredSize = columnSize
        spacing = 1
        decorations { +box(title = "Toggles on panel") }
    }
    root.addComponent(left)
    root.addComponent(right)
    listOf(left, right).forEach { column ->
        val invisible = toggleButton { +"Click Me"; decorations { +side() } }
        invisible.onActivated { invisible.isHidden = true }
        val disabled = toggleButton { +"Disabled" }
        column.addComponents(
            toggleButton { +"Default" },
            toggleButton { +"Boxed"; decorations { +box() } },
            toggleButton {
                +"Too long name for button"
                decorations { +box(); +shadow() }
                preferredSize = Size(10, 4)
            },
            toggleButton { +"Boxed+shadow"; decorations { +box(); +shadow() } },
            invisible,
            disabled,
        )
        disabled.isDisabled = true
    }
    ExampleView(root)
}
