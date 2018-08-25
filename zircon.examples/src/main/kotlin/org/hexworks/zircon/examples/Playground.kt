package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Consumer

object Playground {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Size.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val component = Components.panel()
                .size(Sizes.create(20, 10))
                .position(Positions.offset1x1())
                .build()

        screen.addComponent(component)


        val btn = Components.button().text("foo").build()
        btn.onMouseReleased(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                println("pressed")
            }
        })

        component.draw(btn)

        screen.applyColorTheme(ColorThemeResource.GAMEBOOKERS.getTheme())

        screen.display()

    }

}
