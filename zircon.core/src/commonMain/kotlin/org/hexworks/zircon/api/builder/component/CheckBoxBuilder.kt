package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class CheckBoxBuilder : ComponentWithTextBuilder<CheckBox, CheckBoxBuilder>(
        initialRenderer = DefaultCheckBoxRenderer(),
        initialText = "",
        reservedSpace = DefaultCheckBoxRenderer.DECORATION_WIDTH
) {

    var labelAlignment: CheckBoxAlignment = CheckBoxAlignment.LEFT

    fun withLabelAlignment(labelAlignment: CheckBoxAlignment) = also {
        this.labelAlignment = labelAlignment
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialText = text,
                labelAlignment = labelAlignment,
        )
    }

    override fun createCopy() = newBuilder()
            .withProps(props.copy())
            .withLabelAlignment(labelAlignment)
            .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}
