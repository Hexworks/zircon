package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.InputField
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultInputField

/**
 * Builds an [InputField]: a [label] above a framed input row of [width] columns (outer), with
 * an [icon] or a [button] at the right end of the row. Only one of the two is shown.
 */
@ZirconDsl
public class InputFieldBuilder : ThemedFragmentBuilder<InputField> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var label: String = ""
    public var text: String = ""
    public var maxLength: Int? = null
    public var icon: Char? = null

    /**
     * The text of the button at the right end; `null` for none.
     */
    public var button: String? = null
    public var buttonRole: ComponentRole = ComponentRole.INPUT_FIELD_BUTTON
    public var onButtonActivated: () -> Unit = {}

    override fun build(): InputField {
        require(width >= 6) { "An input field needs at least 6 columns" }
        return DefaultInputField(
            width = width,
            label = label,
            initialText = text,
            maxLength = maxLength,
            icon = icon,
            buttonText = button,
            buttonRole = buttonRole,
            onButtonActivated = onButtonActivated,
            theme = theme,
        ).also { it.root.moveTo(position) }
    }
}

public fun inputField(init: InputFieldBuilder.() -> Unit): InputField = InputFieldBuilder().apply(init).build()

public fun AnyContainerBuilder.inputField(init: InputFieldBuilder.() -> Unit): InputField =
    buildFragmentFor(this, InputFieldBuilder(), init)
