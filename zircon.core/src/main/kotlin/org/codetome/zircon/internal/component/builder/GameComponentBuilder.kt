package org.codetome.zircon.internal.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.GameComponent
import org.codetome.zircon.api.game.ProjectionMode
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.internal.component.impl.DefaultGameComponent
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.api.util.Maybe

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameComponentBuilder(private var gameArea: Maybe<GameArea> = Maybe.empty(),
                                private var projectionMode: ProjectionMode = DEFAULT_PROJECTION_MODE,
                                private var visibleSize: Size3D = Size3D.ONE,
                                private var font: Font = FontSettings.NO_FONT,
                                private var position: Position = Position.defaultPosition(),
                                private var componentStyleSet: ComponentStyleSet = ComponentStyleSetBuilder.DEFAULT) : Builder<GameComponent> {

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

    fun font(font: Font) = also {
        this.font = font
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
                initialFont = font,
                position = position,
                componentStyleSet = componentStyleSet)
    }

    companion object {

        val DEFAULT_PROJECTION_MODE = ProjectionMode.TOP_DOWN

        fun newBuilder() = GameComponentBuilder()
    }
}
