package org.codetome.zircon.api.data

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [Tile]s.
 * The layers are ordered from bottom to top.
 */
data class Block(
        val position: Position3D,
        val top: Tile = Tile.empty(),
        val back: Tile = Tile.empty(),
        val front: Tile = Tile.empty(),
        val bottom: Tile = Tile.empty(),
        val layers: MutableList<Tile> = mutableListOf())
