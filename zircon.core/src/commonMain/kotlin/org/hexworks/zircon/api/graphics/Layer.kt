package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Hideable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.behavior.Identifiable
import org.hexworks.zircon.internal.graphics.InternalLayer
import kotlin.jvm.JvmStatic

/**
 * A [Layer] is a [TileGraphics] which can be positioned and moved over a [TileGrid].
 * With [Layer]s one can create a quasi 3D effect (like top down oblique projections).
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Layer : Boundable, Hideable, Identifiable, TileGraphics, TilesetOverride {

    override fun createCopy(): Layer

    /**
     * Returns this [Layer] as an [InternalLayer] that represents Zircon's internal API.
     */
    fun asInternalLayer(): InternalLayer

    /**
     * Same as [TileGraphics.getTileAt] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [Layer]).
     */
    fun getAbsoluteTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [TileGraphics.draw] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [Layer]).
     */
    fun drawAbsoluteTileAt(position: Position, tile: Tile)

    companion object {

        @JvmStatic
        fun newBuilder() = LayerBuilder()
    }
}
