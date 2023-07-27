package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.data.Position
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

        val simpleBtn = ButtonBuilder().apply {
            +"Button"
            decorations {
                +side()
            }
            alignment = positionalAlignment(1, 3)
        }

        val boxedBtn = ButtonBuilder().apply {
            +"Boxed Button"
            decorations {
                +box()
            }
            alignment = positionalAlignment(1, 5)
        }
        val tooLongBtn = ButtonBuilder().apply {
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

        val overTheTopBtn = ButtonBuilder().apply {
            +"Over the top button"
            decorations {
                +box(boxType = BoxType.DOUBLE)
                +shadow()
            }
            alignment = positionalAlignment(1, 14)
        }

        screen.addComponent(simpleBtn)
        simpleBtn.position = Position.create(1, 1)
        panel.addComponent(simpleBtn)

        screen.addComponent(boxedBtn)
        boxedBtn.position = Position.create(1, 3)
        panel.addComponent(boxedBtn)

        screen.addComponent(tooLongBtn)
        tooLongBtn.position = Position.create(1, 7)
        panel.addComponent(tooLongBtn)

        screen.addComponent(overTheTopBtn)
        overTheTopBtn.position = Position.create(1, 12)
        panel.addComponent(overTheTopBtn)
    }

}
