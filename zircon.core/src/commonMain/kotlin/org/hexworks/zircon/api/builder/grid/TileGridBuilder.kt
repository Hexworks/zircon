package org.hexworks.zircon.api.builder.grid

import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen

/**
 * Builds [TileGrid]s.
 */
@ZirconDsl
public class TileGridBuilder : Builder<TileGrid> {

    public var config: AppConfig = AppConfig.defaultAppConfig()

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    public fun buildScreen(): Screen = TileGridScreen(build().asInternal())

    override fun build(): TileGrid {
        return DefaultTileGrid(config)
    }
}

/**
 * Creates a new [TileGridBuilder] using the builder DSL and returns it.
 */
public fun tileGrid(init: TileGridBuilder.() -> Unit): TileGrid =
    TileGridBuilder().apply(init).build()

public fun TileGridBuilder.withAppConfig(init: AppConfigBuilder.() -> Unit): TileGridBuilder = apply {
    config = AppConfigBuilder().apply(init).build()
}