package org.codetome.zircon.api.game

import org.codetome.zircon.api.TextCharacter

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [TextCharacter]s.
 * The layers are ordered from bottom to top.
 */
data class Block(val position: Position3D,
                 val top: TextCharacter = TextCharacter.empty(),
                 val back: TextCharacter = TextCharacter.empty(),
                 val front: TextCharacter = TextCharacter.empty(),
                 val bottom: TextCharacter = TextCharacter.empty(),
                 val layers: MutableList<TextCharacter> = mutableListOf()) // TODO: mutable
