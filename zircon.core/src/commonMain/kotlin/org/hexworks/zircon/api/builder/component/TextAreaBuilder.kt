package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class TextAreaBuilder : ComponentWithTextBuilder<TextArea, TextAreaBuilder>(
    initialRenderer = DefaultTextAreaRenderer(),
    initialText = ""
) {

    override fun build(): TextArea {
        return DefaultTextArea(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        )
    }

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = TextAreaBuilder()
    }
}
