package org.hexworks.zircon.api.builder.grid

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen

/**
 * Builds [TileGrid]s.
 */
@ZirconDsl
class TileGridBuilder : Builder<TileGrid> {

    var config: AppConfig = AppConfig.defaultAppConfig()

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen = TileGridScreen(build().asInternal())

    override fun build(): TileGrid {
        return DefaultTileGrid(config)
    }
}

/**
 * Creates a new [TileGridBuilder] using the builder DSL and returns it.
 */
fun tileGrid(init: TileGridBuilder.() -> Unit): TileGrid =
    TileGridBuilder().apply(init).build()

fun TileGridBuilder.withAppConfig(init: AppConfigBuilder.() -> Unit) = apply {
    config = AppConfigBuilder().apply(init).build()
}