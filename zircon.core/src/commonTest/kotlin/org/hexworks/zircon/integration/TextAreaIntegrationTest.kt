package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.component.buildTextArea
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.screen.Screen

class TextAreaIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = buildPanel {
            decorations {
                +box()
            }
            withPreferredSize {
                width = 28
                height = 28
            }
            position = Position.create(31, 1)
        }
        screen.addComponent(panel)

        screen.addComponent(
            buildTextArea {
                text = "Some text"
                withPreferredSize {
                    width = 13
                    height = 5
                }
                position = Position.create(2, 2)
            }
        )
        panel.addComponent(
            buildTextArea {
                text = "Some text"
                withPreferredSize {
                    width = 13
                    height = 5
                }
                position = Position.create(2, 2)
            }
        )

        screen.addComponent(
            buildTextArea {
                text = "Some other text"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                withPreferredSize {
                    width = 13
                    height = 7
                }
                position = Position.create(2, 8)
            }
        )
        panel.addComponent(
            buildTextArea {
                text = "Some other text"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                withPreferredSize {
                    width = 13
                    height = 7
                }
                position = Position.create(2, 8)
            }
        )
    }

}
