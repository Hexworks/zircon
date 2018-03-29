package org.codetome.zircon.api

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.game.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [TextCharacter]s.
 * The layers are ordered from bottom to top.
 */
data class Block(val position: Position3D,
                 val top: TextCharacter = TextCharacterBuilder.EMPTY,
                 val back: TextCharacter = TextCharacterBuilder.EMPTY,
                 val front: TextCharacter = TextCharacterBuilder.EMPTY,
                 val bottom: TextCharacter = TextCharacterBuilder.EMPTY,
                 val layers: MutableList<TextCharacter> = mutableListOf()) // TODO: mutable
