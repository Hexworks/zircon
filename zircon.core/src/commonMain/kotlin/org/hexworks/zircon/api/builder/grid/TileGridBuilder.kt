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
open class TileGridBuilder(
    private val config: AppConfig
) : Builder<TileGrid> {

    @Deprecated("obsolete", ReplaceWith("n/a"))
    fun withSize(size: Size): TileGridBuilder = error("use withConfig instead")

    @Deprecated("obsolete", ReplaceWith("n/a"))
    fun withSize(width: Int, height: Int): TileGridBuilder = error("use withConfig instead")

    /**
     * Sets a tileset for this [TileGrid].
     */
    @Deprecated("obsolete", ReplaceWith("n/a"))
    fun withTileset(tileset: TilesetResource): TileGridBuilder = error("use withConfig instead")

    /**
     * Creates a [TileGrid] using this builder's settings and immediately wraps it up in a [Screen].
     */
    fun buildScreen(): Screen = TileGridScreen(build() as InternalTileGrid)

    override fun build(): TileGrid {
        return ThreadSafeTileGrid(config)
    }

    override fun createCopy(): TileGridBuilder = TileGridBuilder(config)

    companion object {

        /**
         * Creates a new [TileGridBuilder].
         */
        @JvmStatic
        @JvmOverloads
        fun newBuilder(config: AppConfig = AppConfig.defaultConfiguration()) = TileGridBuilder(config)
    }
}
