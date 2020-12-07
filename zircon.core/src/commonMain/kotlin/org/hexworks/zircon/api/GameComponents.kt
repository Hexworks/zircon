@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * This object is a facade that can be used to create builders for [GameArea] and [GameComponent].
 */
@Beta
// TODO: add to the docs that the GameComponent was removed and now a ComponentRenderer can be created instead
object GameComponents {

    /**
     * Creates a new [GameAreaBuilder].
     */
    @JvmStatic
    fun <T : Tile, B : Block<T>> newGameAreaBuilder() = GameAreaBuilder<T, B>()

    @JvmStatic
    @JvmOverloads
    fun <C : Component, T : Tile, B : Block<T>> newGameAreaComponentRenderer(
        gameArea: GameArea<T, B>,
        projectionMode: ObservableValue<ProjectionMode> = ProjectionMode.TOP_DOWN.toProperty(),
        fillerTile: Tile = Tile.defaultTile()
    ): ComponentRenderer<C> = GameAreaComponentRenderer(gameArea, projectionMode, fillerTile)

}
