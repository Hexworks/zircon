package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.component.buildParagraph
import org.hexworks.zircon.api.builder.component.buildVbox
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.component.addParagraph
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

class LogAreaIntegrationTest : ComponentIntegrationTestBase(size = Size.create(70, 30)) {

    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(buildVbox {
            decorations {
                +box(title = "Log")
            }
            withPreferredSize {
                width = 60
                height = 25
            }

            val logArea = logArea {
                withPreferredSize {
                    width = 58
                    height = 23
                }
            }

            logArea.addParagraph("This is a simple log row")
            logArea.addParagraph("This is yet another log row")
            logArea.addNewRows(2)

            logArea.addInlineRow(
                listOf(
                    buildParagraph { +"This is a log row with a " },
                    buildButton {
                        +"Button"
                        decorations { }
                    },
                )
            )

            logArea.addNewRows(2)
            logArea.addParagraph("This is a long log row, which gets wrapped, since it is long")
        })
    }
}
