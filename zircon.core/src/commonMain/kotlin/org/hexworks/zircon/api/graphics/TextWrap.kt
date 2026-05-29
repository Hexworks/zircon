package org.hexworks.zircon.api.graphics

/**
 * Represents the possible word wrapping options for
 * [CharacterTileString]s.
 */
enum class TextWrap {
    NO_WRAPPING,

    /**
     * WRAP will wrap text at the end of the line.
     */
    WRAP,

    /**
     * WORD_WRAP will wrap text at the end of a word.
     */
    WORD_WRAP
}
