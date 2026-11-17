package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * A tab: a radio button while unselected, a label while selected, swapped in a panel. Both
 * carry the `TAB` role and the bar's orientation so a [ComponentTheme] can draw them (a theme
 * renderer tells them apart by `Selectable`); the default theme's renderer is the 1.x look, a
 * box around the text.
 */
public class DefaultTab internal constructor(
    key: String,
    initialText: String,
    width: Int,
    theme: ComponentTheme = ZirconDefaults.current.componentTheme,
    vertical: Boolean = false,
) : Tab {

    private val components = ComponentFactory { theme }
    private val tabOrientation = if (vertical) Orientation.VERTICAL else Orientation.HORIZONTAL

    override val tabButton: BinarySelector = components.radioButton {
        role = ComponentRole.TAB
        orientation = tabOrientation
        text = initialText
        this.key = key
        preferredSize = Size(width, 3)
    }

    private val label = components.label {
        role = ComponentRole.TAB
        orientation = tabOrientation
        text = initialText
        preferredSize = Size(width, 3)
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
}
