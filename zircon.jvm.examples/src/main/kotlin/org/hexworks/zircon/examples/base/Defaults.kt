@file:Suppress("UNUSED_PARAMETER")

package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.ColorThemeResource
import java.util.*
import kotlin.random.asKotlinRandom

private val RANDOM = Random()

private val DEFAULT_TILESET_SIZE = listOf(16, 20).random(RANDOM.asKotlinRandom())

val DEFAULT_THEME = ColorThemeResource.values().random(RANDOM.asKotlinRandom())
val TILESETS: List<TilesetResource> = BuiltInCP437TilesetResource.values().filter {
    it.width == DEFAULT_TILESET_SIZE && it.height == DEFAULT_TILESET_SIZE
}
val DEFAULT_TILESET = TILESETS[RANDOM.nextInt(TILESETS.size)]

val GRID_SIZE = create(60, 40)

fun displayScreen(
        theme: ColorTheme = DEFAULT_THEME.getTheme(),
        tileset: TilesetResource = DEFAULT_TILESET
) = SwingApplications.startTileGrid(newBuilder()
        .withDefaultTileset(tileset)
        .enableBetaFeatures()
        .withSize(Defaults.GRID_SIZE)
        .build())
        .toScreen().apply {
            this.theme = theme
            display()
        }

fun startTileGrid(
        theme: ColorTheme = DEFAULT_THEME.getTheme(),
        tileset: TilesetResource = DEFAULT_TILESET
) = SwingApplications.startTileGrid(newBuilder()
        .withDefaultTileset(tileset)
        .enableBetaFeatures()
        .withSize(Defaults.GRID_SIZE)
        .build())
