package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.HalfBlockDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE
import org.hexworks.zircon.api.screen.Screen

class LabelsIntegrationTest : ComponentIntegrationTestBase() {


    override fun buildScreenContent(screen: Screen) {
        screen.addComponent(Components.label()
                .withText("Foobar")
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Barbaz wombat")
                .withSize(Sizes.create(5, 2))
                .withPosition(Positions.create(2, 6))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withPosition(Positions.create(2, 10))
                .build())

        screen.addComponent(Components.label()
                .withText("Qux")
                .wrapWithShadow(true)
                .withBoxType(DOUBLE)
                .wrapWithBox(true)
                .withPosition(Positions.create(15, 2))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorations(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(DOUBLE),
                        BoxDecorationRenderer(SINGLE))
                .withPosition(Positions.create(15, 7))
                .build())

        screen.addComponent(Components.label()
                .withText("Wtf")
                .withDecorations(
                        ShadowDecorationRenderer(),
                        HalfBlockDecorationRenderer())
                .withPosition(Positions.create(15, 14))
                .build())
    }
}
