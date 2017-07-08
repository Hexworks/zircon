package org.codetome.zircon.terminal.swing

import org.codetome.zircon.TerminalPosition
import java.util.*

class DirtyCellsLookupTable {
    private val table: MutableList<BitSet>
    private var firstRowIndex: Int = 0
    private var allDirty: Boolean = false

    fun isAllDirty() = allDirty

    init {
        table = ArrayList<BitSet>()
        firstRowIndex = -1
        allDirty = false
    }

    internal fun resetAndInitialize(firstRowIndex: Int, lastRowIndex: Int, columns: Int) {
        this.firstRowIndex = firstRowIndex
        this.allDirty = false
        val rows = lastRowIndex - firstRowIndex + 1
        while (table.size < rows) {
            table.add(BitSet(columns))
        }
        while (table.size > rows) {
            table.removeAt(table.size - 1)
        }
        for (index in table.indices) {
            if (table[index].size() != columns) {
                table[index] = BitSet(columns)
            } else {
                table[index].clear()
            }
        }
    }

    internal fun setAllDirty() {
        allDirty = true
    }

    internal fun setDirty(position: TerminalPosition) {
        if (position.row < firstRowIndex || position.row >= firstRowIndex + table.size) {
            return
        }
        val tableRow = table[position.row - firstRowIndex]
        if (position.column < tableRow.size()) {
            tableRow.set(position.column)
        }
    }

    internal fun setRowDirty(rowNumber: Int) {
        val row = table[rowNumber - firstRowIndex]
        row.set(0, row.size())
    }

    internal fun setColumnDirty(column: Int) {
        table
                .filter { column < it.size() }
                .forEach { it.set(column) }
    }

    internal fun isDirty(row: Int, column: Int): Boolean {
        if (row < firstRowIndex || row >= firstRowIndex + table.size) {
            return false
        }
        val tableRow = table[row - firstRowIndex]
        if (column < tableRow.size()) {
            return tableRow.get(column)
        } else {
            return false
        }
    }
}