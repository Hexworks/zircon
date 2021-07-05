package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ButtonBuilder : ComponentWithTextBuilder<Button, ButtonBuilder>(
        initialRenderer = DefaultButtonRenderer(),
        initialText = ""
) {

    init {
        decoration = side()
    }

    override fun build(): Button {
        return DefaultButton(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialText = text,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}
