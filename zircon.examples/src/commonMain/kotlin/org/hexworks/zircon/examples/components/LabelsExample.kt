package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Labels: plain, boxed, shadowed and disabled. */
public val labelsExample: Example = Example("Labels") { context ->
    ExampleView(context.twoColumns { box ->
        box.addComponent(label { +"Default" })
        box.addComponent(label {
            +"Boxed"
            decorations { +box() }
        })
        box.addComponent(label {
            +"Shadowed"
            decorations { +shadow() }
        })
        val disabled = label { +"Disabled" }
        box.addComponent(disabled)
        disabled.isDisabled = true
    })
}
