package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.ColorThemeResource
import java.util.*

private val RANDOM = Random()

private val TILESET_SIZES = listOf(16, 20)
private val TILESET_SIZE = TILESET_SIZES[RANDOM.nextInt(TILESET_SIZES.size)]

val THEME = ColorThemeResource.values()[RANDOM.nextInt(ColorThemeResource.values().size)]
val TILESETS: List<TilesetResource> = BuiltInCP437TilesetResource.values().filter {
    it.width == TILESET_SIZE && it.height == TILESET_SIZE
}
val TILESET = TILESETS[RANDOM.nextInt(TILESETS.size)]

val GRID_SIZE = create(60, 40)

fun displayScreen(
        theme: ColorTheme = THEME.getTheme(),
        tileset: TilesetResource = TILESET
) = startTileGrid(newBuilder()
        .withDefaultTileset(tileset)
        .enableBetaFeatures()
        .withSize(Defaults.GRID_SIZE)
        .build())
        .toScreen().apply {
            this.theme = theme
            display()
        }