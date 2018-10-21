package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class CheckBoxesIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleCheckBox = Components.checkBox()
                .withText("Check me")
                .withPosition(Positions.create(2, 2))

        screen.addComponent(simpleCheckBox)
        panel.addComponent(simpleCheckBox)

        val decoratedCheckBox = Components.checkBox()
                .withText("Check me")
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withPosition(Positions.create(2, 4))

        screen.addComponent(decoratedCheckBox)
        panel.addComponent(decoratedCheckBox)

        val shadowedCheckBox = Components.checkBox()
                .withText("Check me")
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 9))

        screen.addComponent(shadowedCheckBox)
        panel.addComponent(shadowedCheckBox)

        val tooLongCheckBox = Components.checkBox()
                .withText("Too long text")
                .withWidth(12)
                .withPosition(Positions.create(2, 13))

        screen.addComponent(tooLongCheckBox)
        panel.addComponent(tooLongCheckBox)

        val overTheTopCheckBox = Components.checkBox()
                .withText("Over the top")
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        BoxDecorationRenderer(BoxType.SINGLE),
                        BoxDecorationRenderer(BoxType.LEFT_RIGHT_DOUBLE))
                .withPosition(Positions.create(2, 16))

        screen.addComponent(overTheTopCheckBox)
        panel.addComponent(overTheTopCheckBox)
    }
}
