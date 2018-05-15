package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.GameComponent
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.ProjectionMode
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.internal.component.impl.DefaultGameComponent
import org.codetome.zircon.internal.font.impl.FontSettings
import java.util.*

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Beta
data class GameComponentBuilder(private var gameArea: Optional<GameArea> = Optional.empty(),
                                private var projectionMode: ProjectionMode = DEFAULT_PROJECTION_MODE,
                                private var visibleSize: Size3D = Size3D.ONE,
                                private var font: Font = FontSettings.NO_FONT,
                                private var position: Position = Position.defaultPosition(),
                                private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT) : Builder<GameComponent> {

    override fun createCopy() = copy()

    fun gameArea(gameArea: GameArea) = also {
        this.gameArea = Optional.of(gameArea)
    }

    fun projectionMode(projectionMode: ProjectionMode) = also {
        this.projectionMode = projectionMode
    }

    fun visibleSize(visibleSize: Size3D) = also {
        this.visibleSize = visibleSize
    }

    fun font(font: Font) = also {
        this.font = font
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    override fun build(): DefaultGameComponent {
        require(gameArea.isPresent) {
            "A GameComponent will only work with a GameArea as backend. Please set one!"
        }
        return DefaultGameComponent(
                gameArea = gameArea.get(),
                projectionMode = projectionMode,
                visibleSize = visibleSize,
                initialFont = font,
                position = position,
                componentStyles = componentStyles)
    }

    companion object {

        @JvmField
        val DEFAULT_PROJECTION_MODE = ProjectionMode.TOP_DOWN

        @JvmStatic
        fun newBuilder() = GameComponentBuilder()
    }
}
