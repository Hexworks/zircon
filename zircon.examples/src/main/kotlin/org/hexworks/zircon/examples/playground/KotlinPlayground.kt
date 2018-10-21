@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource

object KotlinPlayground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437TilesetResource.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .withCursorColor(TileColors.fromString("#ff8844"))
                .withBlinkLengthInMilliSeconds(500)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorBlinking(true)
                .withDebugMode(true)
                .build())

        tileGrid.putCursorAt(Positions.offset1x1())
        tileGrid.setCursorVisibility(true)

//        val screen = ScreenBuilder.createScreenFor(tileGrid)
//        screen.applyColorTheme(ColorThemes.cyberpunk())
//        screen.display()
//        screen.putCursorAt(Positions.offset1x1())
//        screen.setCursorVisibility(true)

    }

}
