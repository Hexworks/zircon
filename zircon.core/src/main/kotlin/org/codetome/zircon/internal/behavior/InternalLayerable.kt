package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer

/**
 * Represents an object which can contain multiple [org.codetome.zircon.api.graphics.Layer]s
 * (which are specialized [org.codetome.zircon.api.graphics.TextImage]s) overlays above the terminal.
 */
interface InternalLayerable : Layerable, Dirtiable, FontOverrideSupport {

    /**
     * Fetches the Z Intersection of the currently present layers
     * relative to its parent [InternalLayerable].
     * A Z intersection is a list of characters present at the same absolute
     * position in a 3d space of [Layer]s ordered from bottom to top.
     */
    fun fetchOverlayZIntersection(absolutePosition: Position) : List<Pair<Font, Tile>>

    // TODO: add dirty checking when adding / removing / popping layers
}
