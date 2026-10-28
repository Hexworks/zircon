package org.hexworks.zircon.api.game

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer

/**
 * A [ComponentRenderer] that draws [gameArea] with the given [projectionMode],
 * using [fillerTile] where the area has nothing to show.
 */
fun <C : Component, T : Tile, B : Block<T>> gameAreaComponentRenderer(
    gameArea: GameArea<T, B>,
    projectionMode: ObservableValue<ProjectionMode>,
    fillerTile: T
): ComponentRenderer<C> = GameAreaComponentRenderer(gameArea, projectionMode, fillerTile)

fun <C : Component, T : Tile, B : Block<T>> gameAreaComponentRenderer(
    gameArea: GameArea<T, B>,
    projectionMode: ProjectionMode,
    fillerTile: T
): ComponentRenderer<C> = gameAreaComponentRenderer(gameArea, projectionMode.toProperty(), fillerTile)
