@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.GameArea


/**
 * This object is a facade that can be used to create builders for [GameArea] and [GameComponent].
 */
@Beta
object GameComponents {

    /**
     * Creates a new [GameAreaBuilder].
     */
    @JvmStatic
    fun <T: Tile, B : Block<T>> newGameAreaBuilder() = GameAreaBuilder<T, B>()

    /**
     * Creates a nwe [GameComponentBuilder].
     */
    @JvmStatic
    fun <T: Tile, B : Block<T>> newGameComponentBuilder() = GameComponentBuilder.newBuilder<T, B>()

}
