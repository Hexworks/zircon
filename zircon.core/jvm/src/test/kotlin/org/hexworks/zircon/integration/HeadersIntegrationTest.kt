package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HeadersIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleHeader = Components.header()
                .withText("Some header")
                .withPosition(Positions.create(2, 2))

        screen.addComponent(simpleHeader)
        panel.addComponent(simpleHeader)

        val decoratedLabel = Components.label()
                .withText("Some label")
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withPosition(Positions.create(2, 4))

        screen.addComponent(decoratedLabel)
        panel.addComponent(decoratedLabel)

        val shadowedHeader = Components.header()
                .withText("Some header")
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 9))

        screen.addComponent(shadowedHeader)
        panel.addComponent(shadowedHeader)

        val tooLongHeader = Components.header()
                .withText("Too long header")
                .withSize(Sizes.create(10, 1))
                .withPosition(Positions.create(2, 13))

        screen.addComponent(tooLongHeader)
        panel.addComponent(tooLongHeader)

        val overTheTopHeader = Components.header()
                .withText("WTF header")
                .withDecorationRenderers(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(BoxType.DOUBLE),
                        BoxDecorationRenderer(BoxType.SINGLE),
                        BoxDecorationRenderer(BoxType.LEFT_RIGHT_DOUBLE))
                .withPosition(Positions.create(2, 16))

        screen.addComponent(overTheTopHeader)
        panel.addComponent(overTheTopHeader)
    }
}
