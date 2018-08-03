package org.codetome.zircon.api.data

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
data class Block<T : Any>(
        val position: Position3D,
        val top: Tile,
        val back: Tile,
        val front: Tile,
        val bottom: Tile,
        val layers: MutableList<Tile> = mutableListOf()) // TODO: mutable
