package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.SimpleModifiers.*

val Tile.isOpaque: Boolean
    get() = backgroundColor.isOpaque

val Tile.isUnderlined: Boolean
    get() = modifiers.contains(Underline)

val Tile.isCrossedOut: Boolean
    get() = modifiers.contains(CrossedOut)

val Tile.isBlinking: Boolean
    get() = modifiers.contains(Blink)

val Tile.hasBorder: Boolean
    get() = modifiers.any { it is Border }

/**
 * Tells whether this [Tile] **is** an empty [Tile]
 * (it is the [Tile.empty] instance).
 */
val Tile.isEmpty: Boolean
    get() = this === Tile.empty()

/**
 * Tells whether this [Tile] **is not** an empty [Tile]
 * (it is not the [Tile.empty] instance).
 */
val Tile.isNotEmpty: Boolean
    get() = this != Tile.empty()


fun List<Tile>.transform(tileTransformer: (Tile) -> Tile): List<Tile> {
    return this.map(tileTransformer::invoke)
}

fun List<Tile>.transformIndexed(transformer: (Int, Tile) -> Tile): List<Tile> {
    return this.mapIndexed { index, tile -> transformer.invoke(index, tile) }
}
