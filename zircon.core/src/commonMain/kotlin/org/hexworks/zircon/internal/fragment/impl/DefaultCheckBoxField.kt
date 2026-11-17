package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.internal.graphics.linesFor
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.CheckBoxField
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width

public class DefaultCheckBoxField internal constructor(
    width: Int,
    label: String,
    description: String,
    initialSelected: Boolean,
    theme: ComponentTheme,
) : CheckBoxField {

    private val components = ComponentFactory { theme }
    override val checkBox: BinarySelector
    override val root: VBox

    init {
        val boxBuilder = components.configure(VBoxBuilder(), ComponentRole.CHECK_BOX_FIELD)
        val inner = maxOf(4, width - boxBuilder.decorationsSize.width)
        checkBox = components.checkBox {
            role = ComponentRole.CHECK_BOX
            +label
        }
        checkBox.isSelected = initialSelected
        val parts = mutableListOf<Component>(checkBox)
        var rows = checkBox.height
        if (description.isNotBlank()) {
            val textWidth = inner - INDENT
            val lines = linesFor(description, textWidth)
            parts += components.paragraph {
                role = ComponentRole.CHECK_BOX_FIELD
                part = Part.DESCRIPTION
                preferredSize = Size(textWidth, lines)
                position = Position(INDENT, 0)
                +description
            }
            rows += lines
        }
        root = boxBuilder.apply {
            preferredContentSize = Size(inner, rows)
            childrenToAdd = parts
        }.build()
    }

    private companion object {
        /** The description starts under the label, past the box and its gap. */
        const val INDENT = 3
    }
}
