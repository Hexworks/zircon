package org.hexworks.zircon.internal.component.impl.texteditor

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.internal.util.BoundedFifoQueue

public sealed class Cell

public data class TextCell(
    val content: CharacterTile,
) : Cell() {
    override fun toString(): String = content.character.toString()
}

/**
 * A cell that represents the end of the line.
 */
public data object EOLCell : Cell() {
    override fun toString(): String = "{EOL}"
}

public data class Line(
    val cells: List<Cell>,
) {

    override fun toString(): String = cells.joinToString("")

    public companion object {
        public fun create(cells: List<Cell> = listOf()): Line {
            return Line(cells.filterIsInstance<TextCell>().plus(EOLCell))
        }
    }
}

public data class EditorState(
    val lines: List<Line>,
    val cursor: Position = Position.ZERO
)

public class TextEditor private constructor(
    initialState: EditorState,
) {

    public var state: EditorState = initialState
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

    public val text: String
        get() = state.lines.joinToString("\n") { line ->
            line.cells.filterIsInstance<TextCell>().map { cell -> cell.content.character }.joinToString("")
        }

    public val tiles: List<List<CharacterTile>>
        get() = state.lines.map { line ->
            line.cells.filterIsInstance<TextCell>().map { it.content }
        }

    public val cursor: Position
        get() = state.cursor

    private val sizeProperty: Property<Size> = state.size.toProperty()

    public var size: Size by sizeProperty.asDelegate()
        private set

    public fun getTileAtOrNull(position: Position): CharacterTile? {
        val (x, y) = position
        val cell = state.lines.getOrNull(y)?.cells?.getOrNull(x)
        return if (cell != null && cell is TextCell) {
            return cell.content
        } else null
    }

    public fun applyTransformation(transformation: Transformation) {
        state = transformation.apply(state)
        size = state.size
        history.add(transformation)
        if (historyIdx <= history.lastIndex) {
            historyIdx++
        }
    }

    public fun undo(): Boolean = if (historyIdx >= 0) {
        // in case historyIdx is 0 this will return an empty list
        // TODO: need to test this though!
        println("history size: ${history.size}, idx: $historyIdx")
        state = history.subList(0, historyIdx)
            .fold(oldState) { state, tx -> tx.apply(state) }
        historyIdx--
        true
    } else false

    public fun redo(): Boolean = if (history.size > 0 && historyIdx < history.lastIndex) {
        historyIdx++
        state = history[historyIdx].apply(state)
        true
    } else false

    public companion object {
        /**
         * Creates a new [TextEditor] from the given [text].
         */
        public fun fromText(text: String): TextEditor {
            return TextEditor(text.toEditorState())
        }

        public fun fromTiles(tiles: List<List<CharacterTile>>): TextEditor {
            val lines = tiles.map {
                Line.create(it.map { tile ->
                    TextCell(tile)
                })
            }
            return TextEditor(
                EditorState(
                    lines = lines,
                    cursor = Position.ZERO
                )
            )
        }
    }
}

public val EditorState.size: Size
    get() {
        val lines = this.lines
        return Size(lines.maxBy { it.cells.size }.cells.size, lines.size)
    }

public val EditorState.cellAtCursor: Cell
    get() {
        val (lines, cursor) = this
        val (x, y) = cursor
        return lines[y].cells[x]
    }

public val EditorState.cursorAtEndOfLine: Boolean
    get() = cellAtCursor is EOLCell

public val EditorState.cursorAtStartOfLine: Boolean
    get() = cursor.x == 0

public val EditorState.cursorAtEndOfDocument: Boolean
    get() = cursorAtEndOfLine && cursor.y == lines.lastIndex

public val EditorState.cursorAtStartOfDocument: Boolean
    get() = cursor == Position.ZERO

public val EditorState.hasNextLine: Boolean
    get() {
        val (lines, cursor) = this
        return lines.lastIndex > cursor.y
    }

public fun EditorState.cellAt(cursor: Position): Cell? {
    val (lines) = this
    val (x, y) = cursor
    return lines.getOrNull(y)?.cells?.getOrNull(x)
}

public fun EditorState.lineAt(pos: Position): Line {
    return lines[pos.y]
}

public fun EditorState.lineAt(y: Int): Line {
    return lines[y]
}

public fun Line.cellAt(x: Int): Cell {
    return cells[x]
}

public val Line.hasText: Boolean
    get() = cells.size > 1

public val Line.lastIndex: Int
    get() = cells.lastIndex

public fun String.toEditorState(): EditorState {
    return EditorState(split("\n").map(String::toLine).toMutableList())
}

public fun String.toLine(): Line = Line.create(map { c ->
    TextCell(
        content = characterTile {
            character = c
        }
    )
})
