package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.examples.base.TwoColumnComponentExample
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer


object ButtonsExample : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        ButtonsExample.show("Buttons Example")
    }

    override fun build(box: VBox) {
        box.addComponent(buildVbox {

            componentRenderer = NoOpComponentRenderer()

            spacing = 1

            button { +"Default" }

            button {
                +"Boxed"
                decoration = box()
            }

            button {
                +"Too long name for button"
                decorations = box() + shadow()
            }

            button {
                +"Half Block"
                decorations = halfBlock() + shadow()
            }

            button {
                +"Hide Me"
                decoration = side()
                onActivated {
                    isHidden = true
                }
            }

            button {
                +"Disable Me"
                onActivated {
                    isDisabled = true
                }
            }
        })
    }
}
