package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.screen.Screen

class TextAreaIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = panel {
            decorations {
                +box()
            }
            preferredSize = Size(28, 28)
            position = Position(31, 1)
        }
        screen.addComponent(panel)

        screen.addComponent(
            textArea {
                text = "Some text"
                preferredSize = Size(13, 5)
                position = Position(2, 2)
            }
        )
        panel.addComponent(
            textArea {
                text = "Some text"
                preferredSize = Size(13, 5)
                position = Position(2, 2)
            }
        )

        screen.addComponent(
            textArea {
                text = "Some other text"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                preferredSize = Size(13, 7)
                position = Position(2, 8)
            }
        )
        panel.addComponent(
            textArea {
                text = "Some other text"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                preferredSize = Size(13, 7)
                position = Position(2, 8)
            }
        )
    }

}
