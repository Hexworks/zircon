package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.builder.fragment.TextAreaFieldBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.TextAreaField
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width

public class DefaultTextAreaField internal constructor(
    width: Int,
    rows: Int,
    label: String,
    initialText: String,
    actions: List<TextAreaFieldBuilder.Action>,
    theme: ComponentTheme,
) : TextAreaField {

    private val components = ComponentFactory { theme }
    override val textArea: TextArea
    override val actions: List<Button>
    override val root: VBox

    init {
        val areaBuilder = components.configure(TextAreaBuilder(), ComponentRole.TEXT_AREA)
        val inner = maxOf(1, width - areaBuilder.decorationsSize.width)
        textArea = areaBuilder.apply {
            preferredContentSize = Size(inner, rows)
            text = initialText
        }.build()
        val outerWidth = textArea.width
        val labelComponent = components.label {
            role = ComponentRole.TEXT_AREA_FIELD
            part = Part.LABEL
            preferredSize = Size(outerWidth, 1)
            +label
        }
        this.actions = actions.map { action ->
            components.button {
                variant = action.variant
                +action.text
                processComponentEvents(ComponentEventType.ACTIVATED) { action.onActivated() }
            }
        }
        val parts = mutableListOf<Component>(labelComponent, textArea)
        var height = 1 + textArea.height
        if (this.actions.isNotEmpty()) {
            val actionsWidth = this.actions.sumOf { it.width } + this.actions.size - 1
            val actionsHeight = this.actions.maxOf { it.height }
            parts += components.hBox {
                role = ComponentRole.TEXT_AREA_FIELD
                part = Part.ACTIONS
                spacing = 1
                preferredContentSize = Size(actionsWidth, actionsHeight)
                position = Position(maxOf(0, outerWidth - actionsWidth), 0)
                childrenToAdd = this@DefaultTextAreaField.actions
            }
            height += actionsHeight
        }
        root = components.vBox {
            role = ComponentRole.TEXT_AREA_FIELD
            preferredContentSize = Size(outerWidth, height)
            childrenToAdd = parts
        }
    }
}
