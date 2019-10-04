package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HeadersIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .withDecorations(box())
                .withSize(Sizes.create(28, 28))
                .withAlignment(positionalAlignment(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleHeader = Components.header()
                .withText("Some header")
                .withAlignment(positionalAlignment(2, 2))

        screen.addComponent(simpleHeader)
        panel.addComponent(simpleHeader)

        val decoratedLabel = Components.label()
                .withText("Some label")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withAlignment(positionalAlignment(Positions.create(2, 4)))

        screen.addComponent(decoratedLabel)
        panel.addComponent(decoratedLabel)

        val shadowedHeader = Components.header()
                .withText("Some header")
                .withDecorations(shadow())
                .withAlignment(positionalAlignment(2, 9))

        screen.addComponent(shadowedHeader)
        panel.addComponent(shadowedHeader)

        val tooLongHeader = Components.header()
                .withText("Too long header")
                .withSize(10, 1)
                .withAlignment(positionalAlignment(2, 13))

        screen.addComponent(tooLongHeader)
        panel.addComponent(tooLongHeader)

        val overTheTopHeader = Components.header()
                .withText("WTF header")
                .withDecorations(
                        shadow(),
                        box(BoxType.DOUBLE),
                        box(BoxType.SINGLE),
                        box(BoxType.LEFT_RIGHT_DOUBLE))
                .withAlignment(positionalAlignment(2, 16))

        screen.addComponent(overTheTopHeader)
        panel.addComponent(overTheTopHeader)
    }
}
