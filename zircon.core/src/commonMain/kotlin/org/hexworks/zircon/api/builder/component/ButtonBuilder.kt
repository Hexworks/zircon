package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ButtonBuilder(
    private var text: String = ""
) : BaseComponentBuilder<Button, ButtonBuilder>(DefaultButtonRenderer()) {

    init {
        withDecorations(side())
    }

    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
            .withWidth(max(text.length, contentSize.width))
    }

    override fun build(): Button {
        return DefaultButton(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}
