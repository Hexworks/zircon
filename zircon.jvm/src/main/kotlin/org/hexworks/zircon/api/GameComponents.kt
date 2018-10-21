package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Tile

object GameComponents {

    /**
     * Creates a new [GameAreaBuilder].
     */
    @JvmStatic
    fun <T : Tile> newGameAreaBuilder() = GameAreaBuilder<T>()

    /**
     * Creates a nwe [GameComponentBuilder].
     */
    @JvmStatic
    fun <T : Tile> newGameComponentBuilder() = GameComponentBuilder.newBuilder<T>()

}
