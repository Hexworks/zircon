package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ToggleButtonBuilder : ComponentWithTextBuilder<ToggleButton, ToggleButtonBuilder>(
        initialRenderer = DefaultToggleButtonRenderer(),
        initialText = "",
        reservedSpace = DefaultToggleButtonRenderer.DECORATION_WIDTH
) {

    var isSelected: Boolean = false

    fun withIsSelected(isSelected: Boolean) = also {
        this.isSelected = isSelected
    }

    override fun build(): ToggleButton {
        return DefaultToggleButton(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialText = text,
                initialSelected = isSelected,
        )
    }

    override fun createCopy() = newBuilder()
            .withProps(props.copy())
            .withText(text)
            .withIsSelected(isSelected)

    companion object {

        @JvmStatic
        fun newBuilder() = ToggleButtonBuilder()
    }
}
