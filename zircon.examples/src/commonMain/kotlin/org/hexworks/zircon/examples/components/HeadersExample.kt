package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Headers: plain, boxed, shadowed and disabled. */
public val headersExample: Example = Example("Headers") { context ->
    ExampleView(context.twoColumns { box ->
        box.addComponent(header { +"Default" })
        box.addComponent(header {
            +"Boxed"
            decorations { +box() }
        })
        box.addComponent(header {
            +"Shadowed"
            decorations { +shadow() }
        })
        val disabled = header { +"Disabled" }
        box.addComponent(disabled)
        disabled.isDisabled = true
    })
}
