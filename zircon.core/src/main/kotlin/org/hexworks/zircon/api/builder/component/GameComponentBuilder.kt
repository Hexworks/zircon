package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.DefaultGameComponent
import kotlin.jvm.JvmStatic

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameComponentBuilder<T : Tile>(
        private var gameArea: Maybe<GameArea<T>> = Maybe.empty(),
        private var projectionMode: ProjectionMode = DEFAULT_PROJECTION_MODE,
        private var visibleSize: Size3D = Size3D.one(),
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<GameComponent<T>, GameComponentBuilder<T>>(commonComponentProperties) {

    override fun createCopy() = copy()

    fun withGameArea(gameArea: GameArea<T>) = also {
        this.gameArea = Maybe.of(gameArea)
    }

    fun withProjectionMode(projectionMode: ProjectionMode) = also {
        this.projectionMode = projectionMode
    }

    fun withVisibleSize(visibleSize: Size3D) = also {
        this.visibleSize = visibleSize
    }

    override fun build(): DefaultGameComponent<T> {
        require(gameArea.isPresent) {
            "A GameComponent will only work with a GameArea as backend. Please set one!"
        }
        return DefaultGameComponent(
                gameArea = gameArea.get(),
                projectionMode = projectionMode,
                size = visibleSize,
                componentMetadata = ComponentMetadata(
                        position = position,
                        size = visibleSize.to2DSize(),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset))
    }

    companion object {

        val DEFAULT_PROJECTION_MODE = ProjectionMode.TOP_DOWN

        @JvmStatic
        fun <T : Tile> newBuilder(): GameComponentBuilder<T> {
            require(RuntimeConfig.config.betaEnabled) {
                "GameComponent is a beta feature. Please enable them when setting up Zircon using an AppConfig."
            }
            return GameComponentBuilder()
        }
    }
}
