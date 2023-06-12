package org.hexworks.zircon.api.builder.grid

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.grid.DefaultTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen
import kotlin.jvm.JvmStatic

/**
 * Builds [TileGrid]s.
 */
class TileGridBuilder private constructor(
    var config: AppConfig = AppConfig.defaultConfiguration()
) : Builder<TileGrid> {

    fun withConfig(config: AppConfig) = also {
        this.config = config
    }

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen = TileGridScreen(build().asInternal())

    override fun build(): TileGrid {
        return DefaultTileGrid(config)
    }

    override fun createCopy(): TileGridBuilder = TileGridBuilder(
        config = config
    )

    companion object {

        /**
         * Creates a new [TileGridBuilder].
         */
        @JvmStatic
        fun newBuilder() = TileGridBuilder()
    }
}
