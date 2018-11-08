package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

/**
 * Represents an object which contains style information.
 */
interface Styleable {

    /**
     * The current foreground color.
     */
    var foregroundColor: TileColor

    /**
     * The current background color.
     */
    var backgroundColor: TileColor

    /**
     * The currently active modifiers.
     */
    var modifiers: Set<Modifier>

    /**
     * Returns a copy of the style information stored in this [Styleable].
     */
    fun toStyleSet(): StyleSet

    /**
     * Copies colors and modifiers from another style.
     */
    fun setStyleFrom(source: StyleSet)

    /**
     * Adds zero or more modifiers to the set of currently active modifiers.
     */
    fun enableModifiers(modifiers: Set<Modifier>)

    /**
     * Adds zero or more modifiers to the set of currently active modifiers.
     */
    fun enableModifiers(vararg modifiers: Modifier) =
            enableModifiers(modifiers.toSet())

    /**
     * Removes zero or more modifiers from the set of currently active modifiers.
     */
    fun disableModifiers(modifiers: Set<Modifier>)

    /**
     * Removes zero or more modifiers from the set of currently active modifiers.
     */
    fun disableModifiers(vararg modifiers: Modifier) =
            disableModifiers(modifiers.toSet())

    /**
     * Removes all active modifiers.
     */
    fun clearModifiers()

    /**
     * Removes all currently active [Modifier]s and sets foreground and background colors back to default.
     */
    fun resetColorsAndModifiers() = setStyleFrom(StyleSet.defaultStyle())


}
