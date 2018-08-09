package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.DefaultGameComponent

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameComponentBuilder(
        private var gameArea: Maybe<GameArea> = Maybe.empty(),
        private var projectionMode: ProjectionMode = DEFAULT_PROJECTION_MODE,
        private var visibleSize: Size3D = Size3D.one(),
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<GameComponent> {

    override fun createCopy() = copy()

    fun gameArea(gameArea: GameArea) = also {
        this.gameArea = Maybe.of(gameArea)
    }

    fun projectionMode(projectionMode: ProjectionMode) = also {
        this.projectionMode = projectionMode
    }

    fun visibleSize(visibleSize: Size3D) = also {
        this.visibleSize = visibleSize
    }

    fun tileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
    }

    override fun build(): DefaultGameComponent {
        require(gameArea.isPresent) {
            "A GameComponent will only work with a GameArea as backend. Please set one!"
        }
        return DefaultGameComponent(
                gameArea = gameArea.get(),
                projectionMode = projectionMode,
                visibleSize = visibleSize,
                initialTileset = tileset,
                position = position,
                componentStyleSet = componentStyleSet)
    }

    companion object {

        val DEFAULT_PROJECTION_MODE = ProjectionMode.TOP_DOWN

        fun newBuilder() = GameComponentBuilder()
    }
}
