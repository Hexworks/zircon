package org.codetome.zircon.terminal

import org.codetome.zircon.Cell
import org.codetome.zircon.terminal.virtual.VirtualTerminal

/**
 * This interface exposes [Cell] iteration functions for a [VirtualTerminal].
 */
interface IterableTerminal : VirtualTerminal {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

    fun forEachCell(fn: (Cell) -> Unit)
}