package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class ButtonIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = buildPanel {
            decorations {
                +box(title = "Buttons on panel")
                +shadow()
            }
            withPreferredSize {
                width = 30
                height = 28
            }
            alignment = positionalAlignment(29, 1)
        }
        screen.addComponent(panel)

        val simpleBtn = buildButton {
            +"Button"
            decorations {
                +side()
            }
            alignment = positionalAlignment(1, 3)
        }

        val boxedBtn = buildButton {
            +"Boxed Button"
            decorations {
                +box()
            }
            alignment = positionalAlignment(1, 5)
        }
        val tooLongBtn = buildButton {
            +"Too long name for button"
            decorations {
                +box()
                +shadow()
            }
            withPreferredSize {
                width = 10
                height = 4
            }
            alignment = positionalAlignment(1, 9)
        }

        val overTheTopBtn = buildButton {
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
