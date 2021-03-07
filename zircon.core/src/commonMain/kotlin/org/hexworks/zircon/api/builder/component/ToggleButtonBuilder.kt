package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ToggleButtonBuilder(
    private var text: String = "",
    private var isSelected: Boolean = false
) : BaseComponentBuilder<ToggleButton, ToggleButtonBuilder>(
    DefaultToggleButtonRenderer()
) {

    fun withText(text: String) = also {
        this.text = text.withNewLinesStripped()
        contentSize = contentSize
            .withWidth(max(this.text.length + DefaultToggleButtonRenderer.DECORATION_WIDTH, contentSize.width))
    }

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

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)
        .withIsSelected(isSelected)

    companion object {

        @JvmStatic
        fun newBuilder() = ToggleButtonBuilder()
    }
}
