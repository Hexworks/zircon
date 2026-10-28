package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.loadAnimation
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * The skull animation (`.zap` archive) looping forever in the middle of the grid.
 */
public suspend fun animationDemo(): TileGrid {
    val tileset = CP437TilesetResources.rexPaint16x16()
    val grid = createTileGrid(
        appConfig {
            size = Size(60, 30)
            defaultTileset = tileset
        }
    )
    val skull = loadAnimation(Resource.create("/animations/skull.zap", ResourceType.PROJECT), tileset).apply {
        loopKind = Animation.InfiniteLoop
        position = Position(20, 5)
    }.build()
    grid.start(skull)
    return grid
}
