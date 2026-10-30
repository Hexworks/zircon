package org.hexworks.zircon.examples.components

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** One-way and two-way bindings between check boxes, and a label bound to a radio group. */
public val dataBindingExample: Example = Example("Data binding") { context ->
    val root = context.oneColumn { box ->
        box.addComponent(hBox {
            preferredSize = box.contentSize
            val columnSize = Size(preferredSize.width / 2, preferredSize.height)
            vBox {
                name = "Left Column"
                preferredSize = columnSize
                vBox {
                    preferredSize = Size(columnSize.width, 4)
                    decorations { +box(BoxType.LEFT_RIGHT_DOUBLE, "One-way Binding") }
                    val c0 = checkBox { +"Check Me!" }
                    val c1 = checkBox { +"I am bound!" }
                    c1.selectedProperty.updateFrom(c0.selectedProperty)
                }
                vBox {
                    preferredSize = Size(columnSize.width, 4)
                    decorations { +box(BoxType.LEFT_RIGHT_DOUBLE, "Two-way Binding") }
                    val c0 = checkBox { +"I am bound!" }
                    val c1 = checkBox { +"I am bound too!" }
                    c1.selectedProperty.bind(c0.selectedProperty)
                }
            }
            vBox {
                name = "Right Column"
                preferredSize = columnSize
                decorations { +box(BoxType.LEFT_RIGHT_DOUBLE, "Selection Binding") }
                val r0 = radioButton { +"One"; key = "One" }
                val r1 = radioButton { +"Two"; key = "Two" }
                val r2 = radioButton { +"Three"; key = "Three" }
                val label = label { preferredSize = Size(10, 1) }
                radioButtonGroup {
                    radioButtons = listOf(r0, r1, r2)
                }.apply {
                    label.textProperty.updateFrom(selectedButtonProperty.bindTransform { it?.text ?: "" })
                }
            }
        })
    }
    ExampleView(root)
}
