package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Buttons with every decoration, one that hides itself and one that disables itself. */
public val buttonsExample: Example = Example("Buttons") { context ->
    ExampleView(context.twoColumns { box ->
        box.addComponent(vBox {
            componentRenderer = NoOpComponentRenderer()
            spacing = 1
            button { +"Default" }
            button {
                +"Boxed"
                decorations { +box() }
            }
            button {
                +"Too long name for button"
                decorations {
                    +box()
                    +shadow()
                }
            }
            button {
                +"Half Block"
                decorations {
                    +side(LEFT_HALF_BLOCK, RIGHT_HALF_BLOCK)
                    +shadow()
                }
            }
            button {
                +"Hide Me"
                decorations { +side() }
                onActivated { isHidden = true }
            }
            button {
                +"Disable Me"
                onActivated { isDisabled = true }
            }
        })
    })
}
