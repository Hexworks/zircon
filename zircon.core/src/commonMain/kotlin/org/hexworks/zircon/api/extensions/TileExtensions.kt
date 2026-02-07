package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.data.tile.GraphicalTile
import org.hexworks.zircon.api.data.tile.ImageTile
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink


val Tile.isOpaque: Boolean
    //! TODO: implement this properly
    get() = this.asCharacterTileOrNull()?.isOpaque ?: false

/**
 * Returns this [Tile] as a [CharacterTile] if possible.
 */
fun Tile.asCharacterTileOrNull(): CharacterTile? = this as? CharacterTile

/**
 * Returns this [Tile] as a [CharacterTile] if possible or the
 * result of calling [orElse] with `this` if it is not.
 */
fun Tile.asCharacterTileOrElse(orElse: (Tile) -> CharacterTile): CharacterTile {
    return asCharacterTileOrNull() ?: orElse(this)
}

/**
 * Returns this [Tile] as an [ImageTile] if possible.
 */

fun Tile.asImageTileOrNull(): ImageTile? = this as? ImageTile

/**
 * Returns this [Tile] as a [ImageTile] if possible or the
 * result of calling [orElse] with `this` if it is not.
 */
fun Tile.asImageTileOrElse(orElse: (Tile) -> ImageTile): ImageTile {
    return asImageTileOrNull() ?: orElse(this)
}

/**
 * Returns this [Tile] as a [GraphicalTile] if possible.
 */
fun Tile.asGraphicalTileOrNull(): GraphicalTile? = this as? GraphicalTile

/**
 * Returns this [Tile] as a [GraphicalTile] if possible or the
 * result of calling [orElse] with `this` if it is not.
 */
fun Tile.asGraphicalTileOrElse(orElse: (Tile) -> GraphicalTile): GraphicalTile {
    return asGraphicalTileOrNull() ?: orElse(this)
}

val CharacterTile.isOpaque: Boolean
    get() = backgroundColor.isOpaque

val CharacterTile.isBlinking: Boolean
    get() = modifiers.contains(Blink)

val Tile.isBlinking: Boolean
    get() = this.asCharacterTileOrNull()?.isBlinking ?: false

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
