package org.codetome.zircon.terminal.config

/**
 * Controls how the bold [org.codetome.zircon.Modifier] will take effect when enabled on a character.
 * Mainly this is controlling if the character should be rendered with a bold font or not.
 * The reason for this is that some characters, notably the lines and double-lines in defined in
 * [org.codetome.zircon.Symbols], usually doesn't look very good with bold font when you try to
 * construct a GUI.
 */
enum class BoldMode {
    /**
     * All characters with bold [org.codetome.zircon.Modifier] enabled will be rendered using a bold font.
     */
    EVERYTHING,
    /**
     * All characters with bold [org.codetome.zircon.Modifier] enabled, except for the characters defined
     * as constants in [org.codetome.zircon.Symbols] class, will be rendered using a bold font.
     */
    EVERYTHING_BUT_SYMBOLS,
    /**
     * Bold font will not be used for characters with [org.codetome.zircon.Modifier] bold enabled.
     */
    NOTHING
}