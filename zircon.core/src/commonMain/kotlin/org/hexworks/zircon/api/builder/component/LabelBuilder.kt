package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class LabelBuilder(
    private var text: String = ""
) : BaseComponentBuilder<Label, LabelBuilder>(DefaultLabelRenderer()) {


    fun withText(text: String) = also {
        this.text = text
            .split(SystemUtils.getLineSeparator())
            .first()
            .split("\n")
            .first()
        contentSize = contentSize
            .withWidth(max(this.text.length, contentSize.width))
    }

    override fun build(): Label {
        return DefaultLabel(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = LabelBuilder()
    }
}
