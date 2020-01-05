package org.hexworks.zircon.internal.game.impl

import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.behavior.TitleHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.data.DefaultLayerState
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.game.InternalGameArea

class DefaultGameComponent<T : Tile, B : Block<T>>(
        componentMetadata: ComponentMetadata,
        initialTitle: String,
        private val renderingStrategy: ComponentRenderingStrategy<GameComponent<T, B>>,
        private val gameArea: InternalGameArea<T, B>)
    : GameComponent<T, B>, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = NoOpComponentRenderer())),
        TitleHolder by TitleHolder.create(initialTitle) {

    override val layerStates: Iterable<LayerState>
        get() = gameArea.fetchImageLayers(tileset).map { imageLayer ->
            DefaultLayerState(
                    tiles = imageLayer.tiles,
                    tileset = tileset,
                    position = absolutePosition,
                    size = imageLayer.size,
                    id = Identifier.randomIdentifier(),
                    isHidden = isHidden)
        }.asIterable() + super.layerStates

    init {
        render()
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .build()
    }
}
