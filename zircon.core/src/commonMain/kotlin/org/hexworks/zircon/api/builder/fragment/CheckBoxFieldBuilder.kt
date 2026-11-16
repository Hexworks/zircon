package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.CheckBoxField
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultCheckBoxField

/**
 * Builds a [CheckBoxField]: a check box with [label], and a [description] wrapped under it
 * when one is given. [width] is the outer width.
 */
@ZirconDsl
public class CheckBoxFieldBuilder : ThemedFragmentBuilder<CheckBoxField> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var label: String = ""
    public var description: String = ""
    public var isSelected: Boolean = false

    override fun build(): CheckBoxField {
        require(width >= 6) { "A check box field needs at least 6 columns" }
        return DefaultCheckBoxField(
            width = width,
            label = label,
            description = description,
            initialSelected = isSelected,
            theme = theme,
        ).also { it.root.moveTo(position) }
    }
}

public fun checkBoxField(init: CheckBoxFieldBuilder.() -> Unit): CheckBoxField = CheckBoxFieldBuilder().apply(init).build()

public fun AnyContainerBuilder.checkBoxField(init: CheckBoxFieldBuilder.() -> Unit): CheckBoxField =
    buildFragmentFor(this, CheckBoxFieldBuilder(), init)
