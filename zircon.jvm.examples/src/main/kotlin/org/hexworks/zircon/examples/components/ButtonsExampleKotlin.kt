package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.halfBlock
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildPanel
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleKotlin
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class ButtonsExampleKotlin : TwoColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ButtonsExampleKotlin().show("Buttons Example")
        }
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
                +"Click Me"
                decoration = side()
            }.apply {
                onActivated {
                    isHidden = true
                }
            }

            button {
                +"Disabled"
            }.apply {
                isDisabled = true
            }
        })
    }
}
