package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.game.InternalGameArea
import org.hexworks.zircon.internal.game.impl.DefaultGameComponent
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class GameComponentBuilder<T : Tile, B : Block<T>>(
        private var gameArea: Maybe<InternalGameArea<T, B>> = Maybe.empty(),
        override val props: CommonComponentProperties<GameComponent<T, B>> = CommonComponentProperties())
    : BaseComponentBuilder<GameComponent<T, B>, GameComponentBuilder<T, B>>() {

    override fun createCopy() = copy(props = props.copy())

    fun withGameArea(gameArea: GameArea<T, B>) = also {
        require(gameArea is InternalGameArea<T, B>) {
            "The supplied game area does not implement the internal game area api."
        }
        this.gameArea = Maybe.of(gameArea)
    }

    override fun build(): DefaultGameComponent<T, B> {
        require(gameArea.isPresent) {
            "Can't build a game component without a game area."
        }
        val gameAreaSize = gameArea.get().visibleSize.to2DSize()
        require(size == gameAreaSize) {
            "Can't build a game component with a size ($size) different from its game area's visible size ($gameAreaSize)."
        }
        return DefaultGameComponent(
                componentMetadata = ComponentMetadata(
                        relativePosition = position,
                        size = size,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<GameComponent<T, B>>),
                gameArea = gameArea.get()).also {
            if (colorTheme !== ColorThemes.default()) {
                it.theme = colorTheme
            }
        }
    }

    companion object {

        @JvmStatic
        fun <T : Tile, B : Block<T>> newBuilder(): GameComponentBuilder<T, B> {
            return GameComponentBuilder()
        }
    }
}
