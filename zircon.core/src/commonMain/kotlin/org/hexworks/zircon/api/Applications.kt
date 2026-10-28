package org.hexworks.zircon.api

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.AppConfig.Companion.defaultAppConfig
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.grid.DefaultTileGrid

/**
 * Creates a new [TileGrid] with the given [config]. A renderer module (for example
 * `zircon.compose`) takes the grid and draws it; core itself never renders.
 */
fun createTileGrid(
    config: AppConfig = defaultAppConfig(),
    eventBus: EventBus = EventBus.create()
): TileGrid = DefaultTileGrid(config = config, eventBus = eventBus)
