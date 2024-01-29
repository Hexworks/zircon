package org.hexworks.zircon.internal.component.impl.texteditor

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.util.BoundedFifoQueue

sealed class Cell

data class TextCell(
    val content: CharacterTile,
) : Cell() {
    override fun toString() = content.character.toString()
}

/**
 * A cell that represents the end of the line.
 */
data object EOLCell : Cell() {
    override fun toString() = "{EOL}"
}

data class Line internal constructor(
    val cells: List<Cell>,
) {

    override fun toString() = cells.joinToString("")

    companion object {
        fun create(cells: List<Cell> = listOf()): Line {
            return Line(cells.filterIsInstance<TextCell>().plus(EOLCell))
        }
    }
}

data class EditorState(
    val lines: List<Line>,
    val cursor: Position = Position.zero()
)

class TextEditor private constructor(
    initialState: EditorState,
) {

    var state = initialState
        private set

    /**
     * The old state contains the state after which [history] can be applied
     * to get to [state].
     */
    private var oldState = state
    private var history = BoundedFifoQueue.create<Transformation>(
        maxElements = 10 // TODO?
    ).apply {
        onKick { transformation ->
            oldState = transformation.apply(oldState)
        }
    }

    /**
     * The idx in [history] that represents the current [state]
     */
    private var historyIdx: Int = -1

    val text: String
        get() = state.lines.joinToString("\n") { line ->
            line.cells.filterIsInstance<TextCell>().map { cell -> cell.content.character }.joinToString("")
        }

    val tiles: List<List<CharacterTile>>
        get() = state.lines.map { line ->
            line.cells.filterIsInstance<TextCell>().map { it.content }
        }

    val cursor: Position
        get() = state.cursor

    val sizeProperty: Property<Size> = state.size.toProperty()

    var size: Size by sizeProperty.asDelegate()
        private set

    fun getTileAtOrNull(position: Position): CharacterTile? {
        val (x, y) = position
        val cell = state.lines.getOrNull(y)?.cells?.getOrNull(x)
        return if (cell != null && cell is TextCell) {
            return cell.content
        } else null
    }

    fun applyTransformation(transformation: Transformation) {
        state = transformation.apply(state)
        size = state.size
        history.add(transformation)
        historyIdx++
    }

    fun undo() {
        if (historyIdx >= 0) {
            // in case historyIdx is 0 this will return an empty list
            // TODO: need to test this though!
            state = history.subList(0, historyIdx)
                .fold(oldState) { state, tx -> tx.apply(state) }
            historyIdx--
        }
    }

    fun redo(): Boolean = if (history.size > 0 && historyIdx < history.lastIndex) {
        historyIdx++
        state = history[historyIdx].apply(state)
        true
    } else false

    companion object {
        /**
         * Creates a new [TextEditor] from the given [text].
         */
        fun fromText(text: String): TextEditor {
            return TextEditor(text.toEditorState())
        }

        fun fromTiles(tiles: List<List<CharacterTile>>): TextEditor {
            val lines = tiles.map {
                Line.create(it.map { tile ->
                    TextCell(tile)
                })
            }
            return TextEditor(
                EditorState(
                    lines = lines,
                    cursor = Position.zero()
                )
            )
        }
    }
}

val EditorState.size: Size
    get() {
        val lines = this.lines
        return size {
            width = lines.maxBy { it.cells.size }.cells.size
            height = lines.size
        }
    }

val EditorState.cellAtCursor: Cell
    get() {
        val (lines, cursor) = this
        val (x, y) = cursor
        return lines[y].cells[x]
    }

fun EditorState.cellAt(cursor: Position): Cell? {
    val (lines) = this
    val (x, y) = cursor
    return lines.getOrNull(y)?.cells?.getOrNull(x)
}

fun EditorState.lineAt(pos: Position): Line {
    return lines[pos.y]
}

fun EditorState.lineAt(y: Int): Line {
    return lines[y]
}

fun Line.cellAt(x: Int): Cell {
    return cells[x]
}

fun String.toEditorState(): EditorState {
    return EditorState(split("\n").map(String::toLine).toMutableList())
}

fun String.toLine() = Line.create(map { c ->
    TextCell(
        content = characterTile {
            character = c
        }
    )
})

val Line.hasText: Boolean
    get() = cells.size > 1

val Line.lastIndex: Int
    get() = cells.lastIndex
