package org.codetome.zircon.behavior

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.layer.Layer
import java.util.*

/**
 * Represents an object which can contain multiple [org.codetome.zircon.graphics.layer.Layer]s
 * (which are specialized [org.codetome.zircon.graphics.TextImage]s) below or above it.
 */
interface Layerable : Boundable {

    /**
     * Adds an overlay on top of the currently present overlays.
     */
    fun addOverlay(layer: Layer)

    /**
     * Adds a background layer below (under) the currenly present background layers.
     */
    fun addBackgroundLayer(layer: Layer)

    /**
     * Reoves and returns the overlay which is at the top of the currently present overlays.
     */
    fun popOverlay(): Optional<Layer>

    /**
     * Removes and returns the overlay which is at the bottom of the currently
     * present background layers.
     */
    fun popBackgroundLayer(): Optional<Layer>

    /**
     * Removes a [Layer] from both the current overlays and background layers.
     */
    fun removeLayer(layer: Layer)

    /**
     * Fetches the Z Intersection of the currently present background layers
     * relative to its parent [Layerable].
     * A Z intersection is a list of characters present at the same absolute
     * position in a 3d space of [Layer]s ordered from bottom to top.
     */
    fun fetchBackgroundZIntersectionForPosition(absolutePosition: Position) : List<TextCharacter>

    /**
     * Fetches the Z Intersection of the currently present overlays
     * relative to its parent [Layerable].
     * A Z intersection is a list of characters present at the same absolute
     * position in a 3d space of [Layer]s ordered from bottom to top.
     */
    fun fetchOverlayZIntersectionForPosition(absolutePosition: Position) : List<TextCharacter>

    // TODO: add dirty checking when adding / removing / popping layers
}