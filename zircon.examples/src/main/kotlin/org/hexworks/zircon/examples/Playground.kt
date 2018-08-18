package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.BaseGameArea
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.util.Maybe
import java.util.*

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
                .addBorder(Borders.newBuilder().build())
                .title("Title")
//                .wrapWithBox()
//                .wrapWithShadow()

        screen.addComponent(component)

        screen.applyColorTheme(ColorThemeResource.GAMEBOOKERS.getTheme())

        screen.display()

    }

}
