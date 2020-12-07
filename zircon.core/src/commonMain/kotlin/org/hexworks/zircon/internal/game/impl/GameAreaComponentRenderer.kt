package org.hexworks.zircon.internal.game.impl

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.TileGraphics

class GameAreaComponentRenderer<C : Component, T : Tile, B : Block<T>>(
    private val gameArea: GameArea<T, B>,
    private val projectionMode: ObservableValue<ProjectionMode>,
    private val fillerTile: Tile
) : ComponentRenderer<C> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<C>) {
        projectionMode.value.gameAreaRenderer.render(
            gameArea = gameArea.asInternalGameArea(),
            graphics = tileGraphics,
            fillerTile = fillerTile
        )
    }
}
