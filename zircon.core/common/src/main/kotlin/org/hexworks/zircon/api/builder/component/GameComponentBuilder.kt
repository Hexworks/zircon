package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.DefaultGameComponent
import kotlin.jvm.JvmStatic

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameComponentBuilder<T : Tile, B : Block<T>>(
        private var gameArea: Maybe<GameArea<T, B>> = Maybe.empty(),
        private var projectionMode: ProjectionMode = DEFAULT_PROJECTION_MODE,
        private var visibleSize: Size3D = Size3D.one(),
        private val commonComponentProperties: CommonComponentProperties<GameComponent<T, B>> = CommonComponentProperties())
    : BaseComponentBuilder<GameComponent<T, B>, GameComponentBuilder<T, B>>(commonComponentProperties) {

    override fun createCopy() = copy()

    fun withGameArea(gameArea: GameArea<T, B>) = also {
        this.gameArea = Maybe.of(gameArea)
    }

    fun withProjectionMode(projectionMode: ProjectionMode) = also {
        this.projectionMode = projectionMode
    }

    fun withVisibleSize(visibleSize: Size3D) = also {
        this.visibleSize = visibleSize
    }

    override fun withComponentRenderer(componentRenderer: ComponentRenderer<GameComponent<T, B>>): GameComponentBuilder<T, B> {
        throw UnsupportedOperationException("Can't set a custom component renderer for a game component.")
    }

    override fun build(): DefaultGameComponent<T, B> {
        require(gameArea.isPresent) {
            "A GameComponent will only work with a GameArea as backend. Please set one!"
        }
        val finalSize = visibleSize.to2DSize()
        return DefaultGameComponent(
                gameArea = gameArea.get(),
                projectionMode = projectionMode,
                componentMetadata = ComponentMetadata(
                        position = fixPosition(finalSize),
                        size = finalSize,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset))
    }

    companion object {

        val DEFAULT_PROJECTION_MODE = ProjectionMode.TOP_DOWN

        @JvmStatic
        fun <T : Tile, B : Block<T>> newBuilder(): GameComponentBuilder<T, B> {
            require(RuntimeConfig.config.betaEnabled) {
                "GameComponent is a beta feature. Please enable them when setting up Zircon using an AppConfig."
            }
            return GameComponentBuilder()
        }
    }
}
