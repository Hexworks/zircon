package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * A tab: a radio button while unselected, a label while selected, swapped in a panel. Both
 * carry the `TAB` role and the bar's orientation so a [ComponentTheme] can draw them (a theme
 * renderer tells them apart by `Selectable`); without an entry the 1.x look (a box around the
 * text) is used.
 */
public class DefaultTab internal constructor(
    key: String,
    initialText: String,
    width: Int,
    theme: ComponentTheme? = null,
    vertical: Boolean = false,
) : Tab {

    private val components = ComponentFactory { theme }
    private val tabOrientation = if (vertical) Orientation.VERTICAL else Orientation.HORIZONTAL

    override val tabButton: RadioButton = components.radioButton {
        role = ComponentRole.TAB
        orientation = tabOrientation
        text = initialText
        this.key = key
        preferredSize = Size(width, 3)
        if (theme.lacks(ComponentRole.TAB, kind = ComponentRole.RADIO_BUTTON)) {
            defaultComponentStyleSet(ComponentStyleSet.DEFAULT_STYLE)
            defaultComponentRenderer(ComponentRenderer { graphics, ctx ->
                graphics.drawText(ctx.component.text, ctx.currentStyle)
            })
            defaultDecorationRenderers(listOf(box(renderingMode = INTERACTIVE)))
        }
    }

    private val label = components.label {
        role = ComponentRole.TAB
        orientation = tabOrientation
        text = initialText
        preferredSize = Size(width, 3)
        if (theme.lacks(ComponentRole.TAB, kind = ComponentRole.LABEL)) {
            defaultComponentRenderer(ComponentRenderer { graphics, ctx ->
                val theme = ctx.theme
                val style = styleSet {
                    backgroundColor = theme.primaryBackgroundColor
                    foregroundColor = theme.primaryForegroundColor
                }
                val filler = Tile.defaultTile().withStyle(style)
                graphics.fill(filler)
                graphics.drawText(ctx.component.text, style, Position.OFFSET_1X1)
            })
        }
    }

    override val root: Panel = components.panel {
        role = ComponentRole.NONE
        preferredSize = tabButton.size
        defaultComponentRenderer(NoOpComponentRenderer())
    }

    private var currentAttachment: AttachedComponent = root.addComponent(tabButton)

    init {
        tabButton.selectedProperty.onChange { (_, isSelected) ->
            currentAttachment.apply {
                detach()
                moveTo(Position.ZERO)
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
        position: Position = Position.ZERO
    ) {
        clear()
        draw(
            tileComposite = characterTileString {
                this.text = text
                size = this@drawText.size
            },
            drawPosition = position
        )
        applyStyle(style)
    }
}
