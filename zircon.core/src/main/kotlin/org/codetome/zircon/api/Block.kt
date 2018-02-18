package org.codetome.zircon.api

import org.codetome.zircon.api.game.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [TextCharacter]s.
 * The layers are ordered from bottom to top.
 */
data class Block(val position: Position3D,
                 val layers: Iterable<TextCharacter>)
