package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.BinarySelector.LabelAlignment
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultBinarySelector

/**
 * Builds a [BinarySelector]. [CheckBoxBuilder], [RadioButtonBuilder] and [ToggleButtonBuilder]
 * preset the role, the space the mark takes and [allowUnselect].
 */
@ZirconDsl
public open class BinarySelectorBuilder(
    initialRole: ComponentRole,
    reservedSpace: Int,
    /** @see BinarySelector.allowUnselect */
    public var allowUnselect: Boolean,
) : ComponentWithTextBuilder<BinarySelector>(
    initialRole = initialRole,
    initialText = "",
    reservedSpace = reservedSpace
) {

    /**
     * The [Selectable.key] of the selector, unique within a group. Derived from the text when
     * left blank ([Selectable.keyFrom]); building fails when neither gives one.
     */
    public var key: String = ""

    public var isSelected: Boolean = false

    public var labelAlignment: LabelAlignment = LabelAlignment.RIGHT

    override fun build(): BinarySelector {
        val key = key.ifBlank { Selectable.keyFrom(text) }
        require(key.isNotBlank()) {
            "A selector without text needs a key."
        }
        return DefaultBinarySelector(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
            key = key,
            initialSelected = isSelected,
            allowUnselect = allowUnselect,
            labelAlignment = labelAlignment,
        ).attachListeners()
    }
}
