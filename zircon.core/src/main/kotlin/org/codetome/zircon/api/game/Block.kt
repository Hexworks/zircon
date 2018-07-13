package org.codetome.zircon.api.game

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.game.Position3D

/**
 * Represents a 3D block at a given [Position3D] which
 * consists of layers of [TextCharacter]s.
 * The layers are ordered from bottom to top.
 */
data class Block(val position: Position3D,
                 val top: TextCharacter = TextCharacterBuilder.empty(),
                 val back: TextCharacter = TextCharacterBuilder.empty(),
                 val front: TextCharacter = TextCharacterBuilder.empty(),
                 val bottom: TextCharacter = TextCharacterBuilder.empty(),
                 val layers: MutableList<TextCharacter> = mutableListOf()) // TODO: mutable
