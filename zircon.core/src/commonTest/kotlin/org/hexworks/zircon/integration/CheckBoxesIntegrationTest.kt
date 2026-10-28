package org.hexworks.zircon.integration

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class CheckBoxesIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(vBox {
            decorations {
                +box()
            }
            preferredSize = Size(28, 28)

            checkBox {
                +"Check me"
            }

            checkBox {
                +"Check me"
                decorations {
                    +box(boxType = BoxType.DOUBLE)
                    +shadow()
                }
            }

            checkBox {
                +"Check me"
                decorations {
                    +shadow()
                }
            }

            checkBox {
                +"Too long text"
                preferredSize = Size(12, 1)
            }

            checkBox {
                +"Over the top"
                decorations {
                    +shadow()
                    +box(BoxType.DOUBLE)
                    +box(BoxType.SINGLE)
                    +box(BoxType.LEFT_RIGHT_DOUBLE)
                }
            }
        })
    }
}
