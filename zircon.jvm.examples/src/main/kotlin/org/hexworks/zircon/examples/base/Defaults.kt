@file:Suppress("UNUSED_PARAMETER")

package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource
import java.util.*

private val DEFAULT_TILESET_SIZE = 16

val GRID_SIZE = create(96, 54)
val DEFAULT_THEME = ColorThemes.gamebookers()
val DEFAULT_TILESET = CP437TilesetResources.rogueYun16x16()
val TILESETS: List<TilesetResource> = BuiltInCP437TilesetResource.values()
    .toList()
    .plus(BuiltInTrueTypeFontResource.values().map { it.toTilesetResource(16) })
    .filter {
        it.width == DEFAULT_TILESET_SIZE && it.height == DEFAULT_TILESET_SIZE
    }


fun displayScreen(
    theme: ColorTheme = DEFAULT_THEME,
    tileset: TilesetResource = DEFAULT_TILESET,
    size: Size = GRID_SIZE
) = SwingApplications.startTileGrid(
    newBuilder()
        .withDefaultTileset(tileset)
        .withSize(size)
        .build()
).toScreen().apply {
    this.theme = theme
    display()
}

fun startTileGrid(
    theme: ColorTheme = DEFAULT_THEME,
    tileset: TilesetResource = DEFAULT_TILESET
) = SwingApplications.startTileGrid(
    newBuilder()
        .withDefaultTileset(tileset)
        .withSize(GRID_SIZE)
        .build()
)
