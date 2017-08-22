package org.codetome.zircon.api

/**
 * Represents a [TextCharacter] which is at a given [Position].
 */
data class Cell(val position: Position,
                val character: TextCharacter)