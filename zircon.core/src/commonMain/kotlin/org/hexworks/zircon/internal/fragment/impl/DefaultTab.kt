package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultTab(
    text: String,
    key: String,
    width: Int = text.length + 2
) : Tab {

    override val tabButton = Components.radioButton()
        .withText(text)
        .withKey(key)
        .withPreferredSize(width, 3)
        .withComponentStyleSet(ComponentStyleSet.defaultStyleSet())
        .withRendererFunction { graphics, ctx ->
            graphics.drawText(text, ctx.currentStyle)
        }.withDecorations(ComponentDecorations.box(renderingMode = INTERACTIVE))
        .build()

    private val label = Components.label()
        .withText(text)
        .withPreferredSize(width, 3)
        .withRendererFunction { graphics, ctx ->
            val theme = ctx.theme
            val style = StyleSet.create(
                foregroundColor = theme.primaryForegroundColor,
                backgroundColor = theme.primaryBackgroundColor
            )
            val filler = Tile.defaultTile().withStyle(style)
            graphics.fill(filler)
            graphics.drawText(text, style, Position.offset1x1())
        }
        .build()

    override val root = Components.panel()
        .withSize(tabButton.size)
        .withComponentRenderer(NoOpComponentRenderer())
        .build()

    private var currentAttachment: AttachedComponent = root.addComponent(tabButton)

    init {
        tabButton.selectedProperty.onChange { (_, isSelected) ->
            currentAttachment.apply {
                detach()
                moveTo(Position.defaultPosition())
            }
            currentAttachment = if (isSelected) {
                root.addComponent(label)
            } else {
                root.addComponent(tabButton)
            }
        }
        tabButton.isSelected = false
    }

    private fun TileGraphics.drawText(
        text: String,
        style: StyleSet,
        position: Position = Position.defaultPosition()
    ) {
        clear()
        draw(
            tileComposite = CharacterTileStrings
                .newBuilder()
                .withText(text)
                .withSize(size)
                .build(),
            drawPosition = position
        )
        applyStyle(style)
    }

}
