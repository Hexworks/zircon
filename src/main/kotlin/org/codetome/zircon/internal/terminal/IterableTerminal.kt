package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.terminal.Terminal

/**
 * This interface exposes [Cell] iteration functions for a [Terminal].
 */
interface IterableTerminal : Terminal {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

    fun forEachCell(fn: (Cell) -> Unit)

}