package org.codetome.zircon.graphics

import org.codetome.zircon.Modifier
import org.codetome.zircon.TextColor

interface StyleSet {

    /**
     * Returns the current background color.
     */
    fun getBackgroundColor(): TextColor

    /**
     * Updates the current background color.
     */
    fun setBackgroundColor(backgroundColor: TextColor)

    /**
     * Returns the current foreground color.
     */
    fun getForegroundColor(): TextColor

    /**
     * Updates the current foreground color.
     */
    fun setForegroundColor(foregroundColor: TextColor)

    /**
     * Adds zero or more modifiers to the set of currently active modifiers.
     */
    fun enableModifiers(vararg modifiers: Modifier)

    /**
     * Activates a [Modifier]. This code modifies a state inside the terminal
     * that will apply to all characters written afterwards, such as bold and italic.
     */
    fun enableModifier(modifier: Modifier)

    /**
     * Removes zero or more modifiers from the set of currently active modifiers.
     */
    fun disableModifiers(vararg modifiers: Modifier)

    /**
     * Deactivates a [Modifier] which has previously been activated.
     */
    fun disableModifier(modifier: Modifier)

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
    fun getActiveModifiers(): Set<Modifier>

    /**
     * Copies colors and modifiers from another style.
     */
    fun setStyleFrom(source: StyleSet)
}

