package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.halfBlock
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.extensions.side
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class ButtonIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .withDecorations(box(title = "Buttons on panel"), shadow())
                .withSize(Size.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)

        val simpleBtn = Components.button()
                .withText("Button")
                .withDecorations(side())
                .withAlignment(positionalAlignment(1, 3))
        val boxedBtn = Components.button()
                .withText("Boxed Button")
                .withDecorations(box())
                .withAlignment(positionalAlignment(1, 5))
        val tooLongBtn = Components.button()
                .withText("Too long name for button")
                .withDecorations(box(), shadow())
                .withAlignment(positionalAlignment(1, 9))
                .withSize(Size.create(10, 4))
        val overTheTopBtn = Components.button()
                .withText("Over the top button")
                .withDecorations(box(boxType = BoxType.DOUBLE), halfBlock(), shadow())
                .withAlignment(positionalAlignment(1, 14))
        val halfBlockBtn = Components.button()
                .withText("Half block button")
                .withDecorations(halfBlock(), shadow())
                .withAlignment(positionalAlignment(Position.create(1, 23)))


        screen.addComponent(simpleBtn)
        panel.addComponent(simpleBtn.withPosition(Position.create(1, 1)).build())

        screen.addComponent(boxedBtn)
        panel.addComponent(boxedBtn.withPosition(Position.create(1, 3)).build())

        screen.addComponent(tooLongBtn)
        panel.addComponent(tooLongBtn.withPosition(Position.create(1, 7)).build())

        screen.addComponent(overTheTopBtn)
        panel.addComponent(overTheTopBtn.withPosition(Position.create(1, 12)).build())

        screen.addComponent(halfBlockBtn)
        panel.addComponent(halfBlockBtn.withPosition(Position.create(1, 21)).build())
    }

}
