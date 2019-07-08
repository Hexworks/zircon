@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Borders
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.border
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.resource.TilesetResource


object KotlinPlayground {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rexPaint16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val zirconGameConfiguration = ZirconGameConfiguration(
                1360, 768, CP437TilesetResources.rogueYun16x16(), ColorThemes.zenburnVanilla())

        val application = SwingApplications.startApplication(zirconGameConfiguration.toZironConfig())
        application.dock(GameView(zirconGameConfiguration))
    }

    class GameView(gameConfiguration: ZirconGameConfiguration) : GameClientView(gameConfiguration) {

        override fun onDock() {
            val screen = screen

            val sidebar = Components.panel()
                    .withSize(18, screen.height - 10)
                    .withDecorations(border(Borders.newBuilder()
                                    .withBorderWidth(2)
                                    .withBorderColor(ANSITileColor.BRIGHT_WHITE)
                                    .build()))
                    .build()
//
            val logArea = Components.logArea()
                    // .withDecorations(new)
                    //   .withTitle("Log")
                    //  .wrapWithBox(true)
                    .withSize(screen.width - sidebar.width, 10)
                    .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                    .build()

            screen.addComponent(logArea)
            screen.addComponent(sidebar)
        }
    }

    open class GameClientView(val gameConfiguration: ZirconGameConfiguration) : BaseView() {

        override val theme = this.gameConfiguration.colorTheme
    }

    class ZirconGameConfiguration(
            screenWidth: Int, screenHeight: Int, private val tileSet: TilesetResource, val colorTheme: ColorTheme)
        : GameConfiguration(screenWidth, screenHeight) {

        fun toZironConfig(): AppConfig {
            return AppConfigs.newConfig()
                    .withDebugMode(false)
                    .enableBetaFeatures()
                    .withDefaultTileset(tileSet)
                    .withSize(screenWidth / tileSet.width, screenHeight / tileSet.height)
                    .build()
        }
    }

    open class GameConfiguration(val screenWidth: Int, val screenHeight: Int)
}
