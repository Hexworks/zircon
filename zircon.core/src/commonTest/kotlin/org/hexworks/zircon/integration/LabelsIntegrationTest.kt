package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE
import org.hexworks.zircon.api.screen.Screen

class LabelsIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(
            buildLabel {
                +"Foobar"
                decorations {
                    +shadow()
                }
                position = Position.create(2, 2)
            }
        )

        screen.addComponent(
            buildLabel {
                +"Barbaz wombat"
                withPreferredSize {
                    width = 5
                    height = 2
                }
                position = Position.create(2, 6)
            }
        )

        screen.addComponent(
            buildLabel {
                +"Qux"
                decorations {
                    +box()
                    +shadow()
                }
                position = Position.create(2, 10)
            }
        )

        screen.addComponent(
            buildLabel {
                +"Qux"
                decorations {
                    +box(DOUBLE)
                    +shadow()
                }
                position = Position.create(15, 2)
            }
        )

        screen.addComponent(
            buildLabel {
                +"Wtf"
                decorations {
                    +box(SINGLE)
                    +box(DOUBLE)
                    +shadow()
                }
                position = Position.create(15, 7)
            }
        )
    }
}
