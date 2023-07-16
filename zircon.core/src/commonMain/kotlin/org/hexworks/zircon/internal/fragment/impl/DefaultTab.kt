package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.buildLabel
import org.hexworks.zircon.api.dsl.component.buildPanel
import org.hexworks.zircon.api.dsl.component.buildRadioButton
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultTab internal constructor(
    key: String,
    initialText: String,
    width: Int,
) : Tab {

    override val tabButton = buildRadioButton {
        text = initialText
        this.key = key
        preferredSize = Size.create(width, 3)
        componentStyleSet = ComponentStyleSet.defaultStyleSet()
        componentRenderer = ComponentRenderer { graphics, ctx ->
            graphics.drawText(ctx.component.text, ctx.currentStyle)
        }
        decoration = box(renderingMode = INTERACTIVE)
    }

    private val label = buildLabel {
        text = initialText
        preferredSize = Size.create(width, 3)
        componentRenderer = ComponentRenderer { graphics, ctx ->
            val theme = ctx.theme
            val style = StyleSet.newBuilder()
                .withBackgroundColor(theme.primaryBackgroundColor)
                .withForegroundColor(theme.primaryForegroundColor)
                .build()
            val filler = Tile.defaultTile().withStyle(style)
            graphics.fill(filler)
            graphics.drawText(ctx.component.text, style, Position.offset1x1())
        }
    }

    override val root = buildPanel {
        preferredSize = tabButton.size
        componentRenderer = NoOpComponentRenderer()
    }

    private var currentAttachment: AttachedComponent = root.addComponent(tabButton)

    init {
        tabButton.selectedProperty.onChange { (_, isSelected) ->
            currentAttachment.apply {
                detach()
                moveTo(Position.defaultPosition())
            }
            currentAttachment = if (isSelected) {
                root.addComponent(this.label)
            } else {
                root.addComponent(tabButton)
            }
        }
        tabButton.isSelected = false
    }

    private fun DrawWindow.drawText(
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
