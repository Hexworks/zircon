package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.buildVbox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HeadersIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {

        screen.addComponent(buildVbox {
            decorations {
                +box()
            }
            withPreferredSize {
                width = 28
                height = 28
            }
            alignment = positionalAlignment(31, 1)

            header {
                +"Some header"
            }

            label {
                +"Some label"
                decorations {
                    +box(boxType = BoxType.DOUBLE)
                    +shadow()
                }
            }

            header {
                +"Some header"
                decorations {
                    +shadow()
                }
            }

            header {
                +"Too long header"
                decorations {
                    +shadow()
                }
                withPreferredSize {
                    width = 10
                    height = 1
                }
            }

            header {
                +"WTF header"
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
