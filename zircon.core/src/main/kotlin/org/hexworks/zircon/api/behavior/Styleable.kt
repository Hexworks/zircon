package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

/**
 * Represents an object which contains style information.
 */
interface Styleable {

    /**
     * Returns a copy of the style information stored in this [StyleSet].
     */
    fun styleSet(): StyleSet

    /**
     * Returns the current background color.
     */
    fun backgroundColor(): TileColor

    /**
     * Updates the current background color.
     */
    fun setBackgroundColor(backgroundColor: TileColor)

    /**
     * Returns the current foreground color.
     */
    fun foregroundColor(): TileColor

    /**
     * Updates the current foreground color.
     */
    fun setForegroundColor(foregroundColor: TileColor)

    /**
     * Adds zero or more modifiers to the set of currently active modifiers.
     */
    fun enableModifiers(modifiers: Set<Modifier>)

    /**
     * Adds zero or more modifiers to the set of currently active modifiers.
     */
    fun enableModifiers(vararg modifiers: Modifier)

    /**
     * Removes zero or more modifiers from the set of currently active modifiers.
     */
    fun disableModifiers(modifiers: Set<Modifier>)

    /**
     * Removes zero or more modifiers from the set of currently active modifiers.
     */
    fun disableModifiers(vararg modifiers: Modifier)

    /**
     * Sets the active modifiers to exactly the set passed in to this method.
     */
    fun setModifiers(modifiers: Set<Modifier>)

    /**
     * Removes all active modifiers.
     */
    fun clearModifiers()

    /**
     * Removes all currently active [Modifier]s and sets foreground and background colors back to default.
     */
    fun resetColorsAndModifiers()

    /**
     * Returns the currently active modifiers.
     */
    fun activeModifiers(): Set<Modifier>

    /**
     * Copies colors and modifiers from another style.
     */
    fun setStyleFrom(source: StyleSet)
}
