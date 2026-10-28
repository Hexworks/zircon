package org.hexworks.zircon.integration

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.component.extensions.addParagraph
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

class LogAreaIntegrationTest : ComponentIntegrationTestBase(size = Size(70, 30)) {

    override fun buildScreenContent(screen: Screen) {
        // Built outside the vBox lambda on purpose: inside it, `logArea {}`, `paragraph {}`
        // and `button {}` would resolve to the child-adding overloads.
        val logArea = logArea {
            preferredSize = Size(58, 23)
        }
        val container = vBox {
            decorations {
                +box(title = "Log")
            }
            preferredSize = Size(60, 25)
        }
        container.addComponent(logArea)
        screen.addComponent(container)

        logArea.addParagraph("This is a simple log row")
        logArea.addParagraph("This is yet another log row")
        logArea.addNewRows(2)

        logArea.addInlineRow(
            listOf(
                paragraph { +"This is a log row with a " },
                button {
                    +"Button"
                    decorations { }
                },
            )
        )

        logArea.addNewRows(2)
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long")
    }
}
