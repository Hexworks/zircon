package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class CheckBoxesIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .withDecorations(box())
                .withPreferredSize(28, 28)
                .withAlignment(positionalAlignment(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleCheckBox = Components.checkBox()
                .withText("Check me")
                .withAlignment(positionalAlignment(2, 2))

        screen.addComponent(simpleCheckBox)
        panel.addComponent(simpleCheckBox)

        val decoratedCheckBox = Components.checkBox()
                .withText("Check me")
                .withDecorations(box(boxType = BoxType.DOUBLE), shadow())
                .withAlignment(positionalAlignment(2, 4))

        screen.addComponent(decoratedCheckBox)
        panel.addComponent(decoratedCheckBox)

        val shadowedCheckBox = Components.checkBox()
                .withText("Check me")
                .withDecorations(shadow())
                .withAlignment(positionalAlignment(2, 9))

        screen.addComponent(shadowedCheckBox)
        panel.addComponent(shadowedCheckBox)

        val tooLongCheckBox = Components.checkBox()
                .withText("Too long text")
                .withPreferredSize(12, 1)
                .withAlignment(positionalAlignment(2, 13))

        screen.addComponent(tooLongCheckBox)
        panel.addComponent(tooLongCheckBox)

        val overTheTopCheckBox = Components.checkBox()
                .withText("Over the top")
                .withDecorations(
                        shadow(),
                        box(BoxType.DOUBLE),
                        box(BoxType.SINGLE),
                        box(BoxType.LEFT_RIGHT_DOUBLE))
                .withAlignment(positionalAlignment(2, 16))

        screen.addComponent(overTheTopCheckBox)
        panel.addComponent(overTheTopCheckBox)
    }
}
