package org.codetome.zircon.terminal

import org.codetome.zircon.Cell

/**
 * This interface exposes [Cell] iteration functions for a [Terminal].
 */
interface IterableTerminal : Terminal {

    fun forEachDirtyCell(fn: (Cell) -> Unit)

    fun forEachCell(fn: (Cell) -> Unit)

}