package org.hexworks.zircon.internal.graphics

/**
 * Breaks [items] into lines of at most [width] items the way `TextWrap.WORD_WRAP` draws text.
 * This is the single word-wrap implementation: `DefaultCharacterTileString` lays tiles out with
 * it and the fragments that size themselves to their text count lines with it, so what is
 * measured is what is drawn.
 *
 * Rules, with [charOf] telling the character an item stands for:
 * - A word that does not fit at the end of a line moves whole to the next line. The separator
 *   before it is dropped.
 * - Separators (`' '`, `'\t'`) between words on one line are kept as the items they were, so a
 *   styled space tile keeps its style. Separators at the start of the text are kept when the
 *   first word fits after them.
 * - A word longer than [width] starts on a fresh line and is broken into [width]-sized pieces.
 * - `'\n'` always starts a new line.
 *
 * A non-positive [width] gives no lines.
 */
internal fun <T> wrapWords(items: List<T>, width: Int, charOf: (T) -> Char): List<List<T>> {
    if (width <= 0) return emptyList()
    val lines = mutableListOf<List<T>>()
    var line = mutableListOf<T>()
    var word = mutableListOf<T>()
    var separators = mutableListOf<T>()

    fun flushWord() {
        if (word.isEmpty()) return
        var rest: List<T> = word
        while (rest.size > width) {
            if (line.isNotEmpty()) {
                lines += line
                line = mutableListOf()
            }
            lines += rest.take(width)
            rest = rest.drop(width)
            separators.clear()
        }
        when {
            line.size + separators.size + rest.size <= width -> {
                line.addAll(separators)
                line.addAll(rest)
            }
            line.isEmpty() -> line.addAll(rest)
            else -> {
                lines += line
                line = rest.toMutableList()
            }
        }
        word = mutableListOf()
        separators = mutableListOf()
    }

    for (item in items) {
        when (charOf(item)) {
            '\n' -> {
                flushWord()
                lines += line
                line = mutableListOf()
                separators.clear()
            }
            ' ', '\t' -> {
                flushWord()
                separators += item
            }
            else -> word += item
        }
    }
    flushWord()
    lines += line
    return lines
}

/** [wrapWords] over the characters of [text]. */
internal fun wrapWords(text: String, width: Int): List<String> =
    wrapWords(text.toList(), width) { it }.map { it.joinToString("") }

/** The rows [text] needs when word-wrapped into [width] columns, at least one. */
internal fun linesFor(text: String, width: Int): Int = maxOf(1, wrapWords(text, width).size)
