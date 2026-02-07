package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.BoxType.BASIC
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.screen.Screen

class PanelsIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            buildPanel {
                decorations {
                    +box()
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(1, 1)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +shadow()
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(1, 8)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box()
                    +shadow()
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(1, 15)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box(DOUBLE)
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(1, 22)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box(BASIC)
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(21, 1)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box(title = "Qux")
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(21, 8)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +shadow()
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(21, 15)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box(BoxType.TOP_BOTTOM_DOUBLE, "Wombat")
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(21, 22)
            }
        )

        screen.addComponent(
            buildPanel {
                decorations {
                    +box(BoxType.LEFT_RIGHT_DOUBLE)
                }
                withPreferredSize {
                    width = 18
                    height = 5
                }
                position = Position.create(41, 1)
            }
        )

        val panel = buildPanel {
            decorations {
                +box(title = "Parent")
            }
            withPreferredSize {
                width = 18
                height = 19
            }
            position = Position.create(41, 8)
        }

        screen.addComponent(panel)

        val nested0 = buildPanel {
            decorations {
                +box(DOUBLE, "Nested 0")
            }
            withPreferredSize {
                width = 14
                height = 15
            }
            position = Position.create(1, 1)
        }

        val nested1 = buildPanel {
            decorations {
                +box(DOUBLE, "Nested 1")
            }
            withPreferredSize {
                width = 10
                height = 11
            }
            position = Position.create(1, 1)
        }
        panel.addComponent(nested0)
        nested0.addComponent(nested1)
    }
}
