package org.hexworks.zircon.api.color.palette

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.Palette

/**
 * Base class for [Palette] implementations.
 * @param E the type of the enum which represents the palette entries
 * @property entries the entries of this palette
 * Make sure that you pass a Map that contains all enum entries!
 */
abstract class BasePalette<E : Enum<E>>(
    vararg entries: Pair<E, Color>
) : Palette<E> {
    private val map = entries.toMap()
    override val keys = map.keys
    override val colors = map.values.toSet()

    override fun get(key: E) = map[key] ?: error("No color for key '$key' found!")
}