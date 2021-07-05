package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class LabelBuilder : ComponentWithTextBuilder<Label, LabelBuilder>(
        initialRenderer = DefaultLabelRenderer(),
        initialText = ""
) {

    override fun build(): Label {
        return DefaultLabel(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialText = text,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = LabelBuilder()
    }
}
