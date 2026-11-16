package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Input
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.InputField
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width

public class DefaultInputField internal constructor(
    width: Int,
    label: String,
    initialText: String,
    maxLength: Int?,
    icon: Char?,
    buttonText: String?,
    buttonVariant: Variant,
    onButtonActivated: () -> Unit,
    theme: ComponentTheme?,
) : InputField {

    private val components = ComponentFactory { theme }
    override val input: Input
    override val button: Button?
    override val root: VBox

    init {
        val rowBuilder = components.configure(HBoxBuilder(), ComponentRole.INPUT_FIELD, part = Part.ROW)
        val inner = maxOf(3, width - rowBuilder.decorationsSize.width)
        val parts = mutableListOf<Component>()
        var inputWidth = inner
        button = buttonText?.let { text ->
            components.button {
                role = ComponentRole.INPUT_FIELD
                variant = buttonVariant
                part = Part.BUTTON
                +text
                processComponentEvents(ComponentEventType.ACTIVATED) { onButtonActivated() }
            }
        }
        val iconComponent = if (button == null && icon != null) components.icon {
            role = ComponentRole.INPUT_FIELD
            part = Part.ICON
            iconTile = characterTile {
                character = icon
                styleSet = StyleSet.defaultStyle()
            }
        } else null
        val trailing: Component? = button ?: iconComponent
        trailing?.let { inputWidth = maxOf(1, inner - it.width - 1) }
        input = components.input {
            role = ComponentRole.INPUT_FIELD
            part = Part.INPUT
            this.width = inputWidth
            this.text = initialText
            this.maxLength = maxLength
        }
        parts += input
        trailing?.let { parts += it }
        val rowHeight = parts.maxOf { it.height }
        val row = rowBuilder.apply {
            spacing = 1
            preferredContentSize = Size(inner, rowHeight)
            childrenToAdd = parts
        }.build()
        val labelComponent = components.label {
            role = ComponentRole.INPUT_FIELD
            part = Part.LABEL
            preferredSize = Size(row.width, 1)
            +label
        }
        root = components.vBox {
            role = ComponentRole.INPUT_FIELD
            preferredContentSize = Size(row.width, 1 + row.height)
            childrenToAdd = listOf(labelComponent, row)
        }
    }
}
