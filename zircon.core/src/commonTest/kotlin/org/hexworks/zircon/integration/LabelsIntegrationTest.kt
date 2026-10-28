package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE
import org.hexworks.zircon.api.screen.Screen

class LabelsIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            label {
                +"Foobar"
                decorations {
                    +shadow()
                }
                position = Position(2, 2)
            }
        )

        screen.addComponent(
            label {
                +"Barbaz wombat"
                preferredSize = Size(5, 2)
                position = Position(2, 6)
            }
        )

        screen.addComponent(
            label {
                +"Qux"
                decorations {
                    +box()
                    +shadow()
                }
                position = Position(2, 10)
            }
        )

        screen.addComponent(
            label {
                +"Qux"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                position = Position(15, 2)
            }
        )

        screen.addComponent(
            label {
                +"Wtf"
                decorations {
                    +box(SINGLE)
                    +box(DOUBLE)
                    +shadow()
                }
                position = Position(15, 7)
            }
        )
    }
}
