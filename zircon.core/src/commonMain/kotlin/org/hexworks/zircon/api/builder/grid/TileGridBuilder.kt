package org.hexworks.zircon.api.builder.grid

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.screen.TileGridScreen
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmOverloads

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
    fun buildScreen(): Screen = TileGridScreen(build() as InternalTileGrid)

    override fun build(): TileGrid {
        return ThreadSafeTileGrid(config)
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

        @Suppress("UNUSED_PARAMETER")
        @Deprecated("We don't pass mandatory parameters anymore. Use the function without a parameter instead.")
        @JvmStatic
        fun newBuilder(config: AppConfig) = TileGridBuilder()
    }
}
