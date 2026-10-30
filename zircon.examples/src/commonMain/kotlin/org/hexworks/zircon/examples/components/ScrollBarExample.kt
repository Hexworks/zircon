package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.verticalScrollBar
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.extensions.addFragment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.fragment.impl.VerticalScrollableList

/** A scrollable list of 70 items, and a scroll bar whose range follows a number input. */
public val scrollBarExample: Example = Example("Scroll bar") { context ->
    val root = panel { preferredSize = context.contentSize }
    val panel = panel {
        decorations { +box(title = "Scrollbar on panel"); +shadow() }
        preferredSize = Size(30, 28)
        alignment = positionalAlignment(29, 1)
    }
    root.addComponent(panel)
    val demoList = (0 until 70).map { "Item $it" }
    val activated = label { +""; position = Position(0, 17); preferredSize = Size(26, 1) }
    val scrollFragment = VerticalScrollableList(
        Size(20, 15),
        Position(0, 1),
        demoList,
        onItemActivated = { item, idx -> activated.text = "Clicked $item ($idx)" },
    )
    panel.addFragment(scrollFragment)
    panel.addComponent(activated)

    val scrollable = Scrollable.create(visibleSize = Size(1, 5), actualSize = Size(1, 200))
    val scrollBar = verticalScrollBar(scrollable) {
        alignment = positionalAlignment(24, 20)
    }
    panel.addComponent(scrollBar)
    val numberInput = horizontalNumberInput {
        preferredSize = Size(4, 1)
        initialValue = 200
        minValue = 5
        maxValue = 500
        alignment = positionalAlignment(1, 22)
    }
    panel.addComponent(numberInput)
    numberInput.currentValueProperty.onChange { scrollable.actualSize = Size(1, it.newValue) }
    panel.addComponent(label { +"Resize Me!"; alignment = positionalAlignment(1, 21) })
    ExampleView(root)
}
