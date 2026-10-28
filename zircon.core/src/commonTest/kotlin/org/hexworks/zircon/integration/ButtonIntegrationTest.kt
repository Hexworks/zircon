package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class ButtonIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = panel {
            decorations {
                +box(title = "Buttons on panel")
                +shadow()
            }
            preferredSize = Size(30, 28)
            alignment = positionalAlignment(29, 1)
        }
        screen.addComponent(panel)

        val simpleBtn = button {
            +"Button"
            decorations {
                +side()
            }
            alignment = positionalAlignment(1, 3)
        }

        val boxedBtn = button {
            +"Boxed Button"
            decorations {
                +box()
            }
            alignment = positionalAlignment(1, 5)
        }
        val tooLongBtn = button {
            +"Too long name for button"
            decorations {
                +box()
                +shadow()
            }
            preferredSize = Size(10, 4)
            alignment = positionalAlignment(1, 9)
        }

        val overTheTopBtn = button {
            +"Over the top button"
            decorations {
                +box(boxType = BoxType.DOUBLE)
                +shadow()
            }
            alignment = positionalAlignment(1, 14)
        }

        panel.addComponent(simpleBtn)
        panel.addComponent(boxedBtn)
        panel.addComponent(tooLongBtn)
        panel.addComponent(overTheTopBtn)
    }

}
