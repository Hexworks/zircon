package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.TextAreaField
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultTextAreaField

/**
 * Builds a [TextAreaField]: a [label] above a text area of [width] columns (outer) and
 * [rows] text rows, with the [action] buttons in a row under it, right-aligned.
 */
@ZirconDsl
public class TextAreaFieldBuilder : ThemedFragmentBuilder<TextAreaField> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var rows: Int = 4
    public var label: String = ""
    public var text: String = ""
    internal val actions = mutableListOf<Action>()

    /**
     * Adds a button under the text area.
     */
    public fun action(text: String, role: ComponentRole = ComponentRole.BUTTON_SECONDARY, onActivated: () -> Unit = {}) {
        actions += Action(text, role, onActivated)
    }

    override fun build(): TextAreaField {
        require(width >= 6) { "A text area field needs at least 6 columns" }
        require(rows >= 1) { "A text area field needs at least 1 row" }
        return DefaultTextAreaField(
            width = width,
            rows = rows,
            label = label,
            initialText = text,
            actions = actions.toList(),
            theme = theme,
        ).also { it.root.moveTo(position) }
    }

    internal class Action(val text: String, val role: ComponentRole, val onActivated: () -> Unit)
}

public fun textAreaField(init: TextAreaFieldBuilder.() -> Unit): TextAreaField = TextAreaFieldBuilder().apply(init).build()

public fun AnyContainerBuilder.textAreaField(init: TextAreaFieldBuilder.() -> Unit): TextAreaField =
    buildFragmentFor(this, TextAreaFieldBuilder(), init)
