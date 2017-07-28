package org.codetome.zircon.terminal

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter

data class Cell(val position: TerminalPosition,
                val character: TextCharacter)