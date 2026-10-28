package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.loadREXFile
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * A REXPaint drawing loaded from this module's resources: each `.xp` layer becomes a grid layer.
 */
public suspend fun rexPaintDemo(): TileGrid {
    val tileset = CP437TilesetResources.rexPaint16x16()
    val grid = createTileGrid(
        appConfig {
            size = Size(60, 30)
            defaultTileset = tileset
        }
    )
    val logo = loadREXFile(Resource.create("/rex_files/zircon_logo.xp", ResourceType.PROJECT))
    logo.toLayerList(tileset).forEach { grid.addLayer(it) }
    return grid
}
