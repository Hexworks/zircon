package org.hexworks.zircon.integration

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.VirtualApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
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
                appConfig = AppConfigBuilder.newBuilder()
                        .withDefaultTileset(tileset)
                        .withSize(size)
                        .build()),
        val screen: Screen = ScreenBuilder.createScreenFor(tileGrid)) {


    @Test
    fun shouldProperlyWorkWithComponent() {
        buildScreenContent(screen)
        screen.display()
        screen.applyColorTheme(theme)
    }


    abstract fun buildScreenContent(screen: Screen)

    companion object {
        val DEFAULT_TILESET_WANDERLUST = CP437TilesetResources.wanderlust16x16()
        val DEFAULT_THEME_CYBERPUNK = ColorThemeResource.CYBERPUNK.getTheme()
        val DEFAULT_SIZE_60X30 = Sizes.create(60, 30)
    }
}
