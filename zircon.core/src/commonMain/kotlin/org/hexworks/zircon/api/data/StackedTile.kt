package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.graphics.Layer

/**
 * This tile implementation contains a *stack* of [Tile]s. This can be used to
 * create compositions of [Tile]s that will be rendered on top of each other
 * (form bottom to top) without having to use [Layer]s.
 */
interface StackedTile : Tile {
}
