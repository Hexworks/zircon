package org.codetome.zircon.internal

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.behavior.Dirtiable
import java.util.*

/**
 * Represents an object which can contain multiple [org.codetome.zircon.graphics.layer.Layer]s
 * (which are specialized [org.codetome.zircon.graphics.TextImage]s) overlays above the terminal.
 */
interface InternalLayerable : Layerable, Dirtiable {

    /**
     * Fetches the Z Intersection of the currently present layers
     * relative to its parent [InternalLayerable].
     * A Z intersection is a list of characters present at the same absolute
     * position in a 3d space of [Layer]s ordered from bottom to top.
     */
    fun fetchOverlayZIntersection(absolutePosition: Position) : List<TextCharacter>

    // TODO: add dirty checking when adding / removing / popping layers
}