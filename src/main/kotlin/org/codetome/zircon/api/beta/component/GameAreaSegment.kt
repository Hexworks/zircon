package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.graphics.TextImage

/**
 * Represents a 2D slice of 3D space within a `GameArea`.
 * Note that a slice can contain multiple layers over each other.
 * Layers are ordered from bottom to top.
 */
@Beta
data class GameAreaSegment(val layers: List<TextImage>, val level: Int)