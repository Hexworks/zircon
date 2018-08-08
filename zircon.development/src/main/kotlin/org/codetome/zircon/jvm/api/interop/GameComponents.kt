package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.builder.component.GameComponentBuilder
import org.codetome.zircon.api.builder.game.GameAreaBuilder

object GameComponents {

    @JvmStatic
    fun newGameAreaBuilder() = GameAreaBuilder()

    @JvmStatic
    fun newGameComponentBuilder() = GameComponentBuilder.newBuilder()

}
