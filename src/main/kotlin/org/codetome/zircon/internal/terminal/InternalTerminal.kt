package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.terminal.Terminal

/**
 * This is the internal API of a [Terminal]
 */
interface InternalTerminal : Terminal {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

}