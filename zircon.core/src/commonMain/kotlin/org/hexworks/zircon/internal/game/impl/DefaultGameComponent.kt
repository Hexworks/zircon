package org.hexworks.zircon.internal.game.impl

import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import org.hexworks.zircon.internal.game.InternalGameArea
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
class DefaultGameComponent<T : Tile, B : Block<T>>(
        componentMetadata: ComponentMetadata,
        initialTitle: String,
        private val renderer: ComponentRenderingStrategy<GameComponent<T, B>>,
        private val gameArea: InternalGameArea<T, B>
) : GameComponent<T, B>, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderer),
        TitleOverride by TitleOverride.create(initialTitle) {

    // TODO: render game area
    @Synchronized
    override fun render(graphics: TileGraphics) {
        (renderer as ComponentRenderingStrategy<Component>).render(this, graphics)
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
