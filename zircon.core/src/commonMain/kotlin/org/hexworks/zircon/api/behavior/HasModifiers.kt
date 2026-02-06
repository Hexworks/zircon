package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

interface HasModifiers<T: Any> {

    val modifiers: Set<Modifier>

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): T

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): T =
        withModifiers(modifiers.toSet())

    /**
     * Convenience method for applying a [StyleSet] to a [org.hexworks.zircon.api.data.Tile].
     */
    fun withStyle(style: StyleSet): T = withModifiers(style.modifiers)


}
