package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.builder.game.GameAreaBuilder

object GameComponents {

    /**
     * Creates a new [GameAreaBuilder].
     */
    @JvmStatic
    fun newGameAreaBuilder() = GameAreaBuilder()

    /**
     * Creates a nwe [GameComponentBuilder].
     */
    @JvmStatic
    fun newGameComponentBuilder() = GameComponentBuilder.newBuilder()

}
