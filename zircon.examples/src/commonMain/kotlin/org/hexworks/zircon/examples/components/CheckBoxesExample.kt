package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Check boxes with every decoration, one that hides itself and a disabled one. */
public val checkBoxesExample: Example = Example("Check boxes") { context ->
    ExampleView(context.twoColumns { box ->
        val invisible = checkBox {
            +"Make me invisible"
            decorations { +side() }
        }
        invisible.onActivated { invisible.isHidden = true }
        val disabled = checkBox { +"Disabled Button" }
        box.addComponent(checkBox { +"Default" })
        box.addComponent(checkBox {
            +"Boxed"
            decorations { +box() }
        })
        box.addComponent(checkBox {
            +"Too long name"
            decorations {
                +box()
                +shadow()
            }
            preferredSize = Size(16, 4)
        })
        box.addComponent(checkBox {
            +"Half block"
            decorations {
                +side(LEFT_HALF_BLOCK, RIGHT_HALF_BLOCK)
                +shadow()
            }
        })
        box.addComponent(invisible)
        box.addComponent(disabled)
        disabled.isDisabled = true
    })
}
