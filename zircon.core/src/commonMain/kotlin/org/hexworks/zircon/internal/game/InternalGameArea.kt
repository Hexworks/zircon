@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea

interface InternalGameArea<T : Tile, B : Block<T>> : GameArea<T, B> {

    val state: GameAreaState<T, B>
}
