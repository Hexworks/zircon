package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.BoxType.BASIC
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.screen.Screen

class PanelsIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            panel {
                decorations {
                    +box()
                }
                preferredSize = Size(18, 5)
                position = Position(1, 1)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +shadow()
                }
                preferredSize = Size(18, 5)
                position = Position(1, 8)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box()
                    +shadow()
                }
                preferredSize = Size(18, 5)
                position = Position(1, 15)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box(DOUBLE)
                }
                preferredSize = Size(18, 5)
                position = Position(1, 22)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box(BASIC)
                }
                preferredSize = Size(18, 5)
                position = Position(21, 1)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box(title = "Qux")
                }
                preferredSize = Size(18, 5)
                position = Position(21, 8)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +shadow()
                }
                preferredSize = Size(18, 5)
                position = Position(21, 15)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box(BoxType.TOP_BOTTOM_DOUBLE, "Wombat")
                }
                preferredSize = Size(18, 5)
                position = Position(21, 22)
            }
        )

        screen.addComponent(
            panel {
                decorations {
                    +box(BoxType.LEFT_RIGHT_DOUBLE)
                }
                preferredSize = Size(18, 5)
                position = Position(41, 1)
            }
        )

        val panel = panel {
            decorations {
                +box(title = "Parent")
            }
            preferredSize = Size(18, 19)
            position = Position(41, 8)
        }

        screen.addComponent(panel)

        val nested0 = panel {
            decorations {
                +box(DOUBLE, "Nested 0")
            }
            preferredSize = Size(14, 15)
            position = Position(1, 1)
        }

        val nested1 = panel {
            decorations {
                +box(DOUBLE, "Nested 1")
            }
            preferredSize = Size(10, 11)
            position = Position(1, 1)
        }
        panel.addComponent(nested0)
        nested0.addComponent(nested1)
    }
}
