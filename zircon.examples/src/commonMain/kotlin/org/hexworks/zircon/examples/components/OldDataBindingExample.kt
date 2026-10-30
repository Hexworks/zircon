package org.hexworks.zircon.examples.components

import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Binds the text of every component on the panel to a master label; Tamper changes it. */
public val oldDataBindingExample: Example = Example("Old binding") { context ->
    val bindings = mutableListOf<Binding<Any>>()
    val root = panel { preferredSize = context.contentSize }
    val panel = panel {
        decorations { +box(title = "Panel title") }
        preferredSize = Size(40, 20)
    }
    val label = label { +"Label"; position = Position(1, 1); preferredSize = Size(20, 1) }
    val header = header { +"Header"; position = Position(1, 2); preferredSize = Size(20, 1) }
    val btn = button { +"Button"; position = Position(1, 3); preferredSize = Size(20, 1) }
    val checkBox = checkBox { +"Check box"; position = Position(1, 4); preferredSize = Size(20, 1) }
    val radioBox = vBox { position = Position(1, 5); preferredSize = Size(20, 1) }
    val option = radioButton { key = "btn0"; +"Radio button" }
    radioBox.addComponent(option)
    val paragraph = paragraph {
        position = Position(1, 6)
        +"This is a very long text on this paragraph, so it will be multi line."
        preferredSize = Size(20, 4)
    }
    root.addComponent(panel)
    val master = label { +"foo"; position = Position(25, 1); preferredSize = Size(10, 1) }
    val tamper = button { +"Tamper"; position = Position(25, 2) }.apply {
        handleMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
            master.textProperty.value = master.text + "x"
            Processed
        }
    }
    @Suppress("UNCHECKED_CAST")
    val bind = button { +"Bind"; position = Position(25, 3) }.apply {
        handleMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
            bindings.add(panel.titleProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(label.textProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(header.textProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(btn.textProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(checkBox.textProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(option.textProperty.bind(master.textProperty) as Binding<Any>)
            bindings.add(paragraph.textProperty.bind(master.textProperty) as Binding<Any>)
            Processed
        }
    }
    val unbind = button { +"Unbind"; position = Position(25, 4) }.apply {
        onActivated {
            bindings.forEach { it.dispose() }
            bindings.clear()
        }
    }
    listOf(label, header, btn, checkBox, radioBox, paragraph, master, tamper, bind, unbind).forEach { panel.addComponent(it) }
    ExampleView(root)
}
