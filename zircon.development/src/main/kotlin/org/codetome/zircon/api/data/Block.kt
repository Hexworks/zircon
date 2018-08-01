package org.codetome.zircon.api.data

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
data class Block<T : Any>(
        val position: Position3D,
        val top: Tile<T>,
        val back: Tile<T>,
        val front: Tile<T>,
        val bottom: Tile<T>,
        val layers: MutableList<Tile<T>> = mutableListOf()) // TODO: mutable
