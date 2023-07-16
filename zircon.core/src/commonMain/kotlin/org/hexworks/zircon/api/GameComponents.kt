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


/**
 * This object is a facade that can be used to create builders for [GameArea] and [GameComponent].
 */
object GameComponents {


    fun <C : Component, T : Tile, B : Block<T>> newGameAreaComponentRenderer(
        gameArea: GameArea<T, B>,
        projectionMode: ObservableValue<ProjectionMode>,
        fillerTile: Tile = Tile.defaultTile()
    ): ComponentRenderer<C> = GameAreaComponentRenderer(gameArea, projectionMode, fillerTile)

    fun <C : Component, T : Tile, B : Block<T>> newGameAreaComponentRenderer(
        gameArea: GameArea<T, B>,
        projectionMode: ProjectionMode,
        fillerTile: Tile = Tile.defaultTile()
    ): ComponentRenderer<C> = newGameAreaComponentRenderer(gameArea, projectionMode.toProperty(), fillerTile)

}
