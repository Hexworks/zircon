package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class CheckBoxBuilder(
    private var text: String = "",
    private var labelAlignment: CheckBoxAlignment = CheckBoxAlignment.RIGHT
) : BaseComponentBuilder<CheckBox, CheckBoxBuilder>(DefaultCheckBoxRenderer()) {

    init {
        contentSize = Size.create(DefaultCheckBoxRenderer.BUTTON_WIDTH, 1)
    }

    fun withText(text: String) = also {
        this.text = text
        val totalSize =
            if (text == "")
                DefaultCheckBoxRenderer.BUTTON_WIDTH
            else
                text.length + DefaultCheckBoxRenderer.DECORATION_WIDTH
        contentSize = contentSize
            .withWidth(max(totalSize, contentSize.width))
    }

    fun withLeftAlignedText() = also {
        labelAlignment = CheckBoxAlignment.LEFT
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
            labelAlignment = labelAlignment,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}
