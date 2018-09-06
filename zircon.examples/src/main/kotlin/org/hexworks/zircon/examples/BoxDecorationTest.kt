package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.impl.wrapping.HalfBlockComponentDecorationRenderer

object BoxDecorationTest {


    @JvmStatic
    fun main(args: Array<String>) {

        val config = AppConfigs.newConfig()
                .defaultSize(Sizes.create(60, 30))
                .build()
        val app = SwingApplications.startApplication(config)

        val screen = Screens.createScreenFor(app.tileGrid)

        screen.addComponent(Components.panel()
                .size(Sizes.create(20, 8))
                .position(Positions.create(1, 1))
                .wrapWithBox()
                .wrapWithShadow()
                .build().apply {
                    addComponent(Components.button()
                            .position(Positions.create(0, 0))
                            .text("Bar")
                            .build())
                    addComponent(Components.checkBox()
                            .position(Positions.create(0, 3))
                            .text("Bar")
                            .build())
                })

        screen.addComponent(Components.button()
                .position(Positions.create(22, 1))
                .wrapWithBox(true)
                .wrapSides(false)
                .text("Foo")
                .build())

        screen.addComponent(Components.button()
                .position(Positions.create(22, 5))
                .wrapSides(false)
                .wrapWithBox(true)
                .boxType(BoxType.DOUBLE)
                .text("Foo")
                .build())

        screen.addComponent(Components.button()
                .position(Positions.create(22, 9))
                .wrapper(HalfBlockComponentDecorationRenderer())
                .text("Qux")
                .build())

        screen.display()
        screen.applyColorTheme(ColorThemes.gamebookers())

    }

}
