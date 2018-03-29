package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet

interface Styleable {

    /**
     * Returns a copy of the style information stored in this [StyleSet].
     */
    fun toStyleSet(): StyleSet

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
    fun getActiveModifiers(): Set<Modifier>

    /**
     * Copies colors and modifiers from another style.
     */
    fun setStyleFrom(source: StyleSet)
}