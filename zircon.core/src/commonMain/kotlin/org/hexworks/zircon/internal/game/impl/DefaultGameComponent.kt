package org.hexworks.zircon.internal.game.impl

import org.hexworks.cobalt.core.api.UUID
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
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import org.hexworks.zircon.internal.data.DefaultLayerState
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.game.InternalGameArea

class DefaultGameComponent<T : Tile, B : Block<T>>(
        componentMetadata: ComponentMetadata,
        initialTitle: String,
        renderingStrategy: ComponentRenderingStrategy<GameComponent<T, B>>,
        private val gameArea: InternalGameArea<T, B>)
    : GameComponent<T, B>, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TitleHolder by TitleHolder.create(initialTitle) {

    override val layerStates: Sequence<LayerState>
        get() = gameArea.imageLayers.map {
            DefaultLayerState(
                    tiles = it.tiles,
                    tileset = tileset,
                    position = absolutePosition,
                    size = it.size,
                    id = UUID.randomUUID(),
                    isHidden = isHidden)
        } + super.layerStates

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
