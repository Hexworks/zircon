package org.codetome.zircon.terminal

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter

data class Cell(val position: Position,
                val character: TextCharacter)