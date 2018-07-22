package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.game.GameAreaBuilder
import org.codetome.zircon.api.builder.component.GameComponentBuilder

object GameComponents {

    @JvmStatic
    fun newGameAreaBuilder() = GameAreaBuilder()

    @JvmStatic
    fun newGameComponentBuilder() = GameComponentBuilder.newBuilder()

}
