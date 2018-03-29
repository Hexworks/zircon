package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.behavior.FontOverrideSupport
import org.codetome.zircon.internal.behavior.InternalLayerable

/**
 * This is the internal API of a [Terminal]
 */
interface InternalTerminal : Terminal, InternalLayerable, FontOverrideSupport {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

    fun forEachCell(fn: (Cell) -> Unit)
}
