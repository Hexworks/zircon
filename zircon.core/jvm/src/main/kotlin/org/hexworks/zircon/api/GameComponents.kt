package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile

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
