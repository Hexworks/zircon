package org.hexworks.zircon.integration

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.VirtualApplications
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.resource.ColorThemeResource
import org.junit.Test

@Suppress("MemberVisibilityCanBePrivate")
abstract class ComponentIntegrationTestBase(
    val theme: ColorTheme = DEFAULT_THEME_CYBERPUNK,
    val size: Size = DEFAULT_SIZE_60X30,
    val tileset: TilesetResource = DEFAULT_TILESET_WANDERLUST,
    val tileGrid: TileGrid = VirtualApplications.startTileGrid(
        appConfig = appConfig {
            defaultTileset = tileset
            this.size = size
        }
    ),
    val screen: Screen = tileGrid.toScreen()
) {

    @Test
    fun shouldProperlyWorkWithComponent() {
        screen.tileset = tileset
        screen.theme = theme
        buildScreenContent(screen)
        screen.display()
    }


    abstract fun buildScreenContent(screen: Screen)

    companion object {
        val DEFAULT_TILESET_WANDERLUST = CP437TilesetResources.wanderlust16x16()
        val DEFAULT_THEME_CYBERPUNK = ColorThemeResource.CYBERPUNK.getTheme()
        val DEFAULT_SIZE_60X30 = Size.create(60, 30)
    }
}
