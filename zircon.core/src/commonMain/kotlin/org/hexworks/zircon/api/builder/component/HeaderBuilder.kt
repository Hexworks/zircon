package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultHeader
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class HeaderBuilder : ComponentWithTextBuilder<Header, HeaderBuilder>(
    initialRenderer = DefaultHeaderRenderer(),
    initialText = ""
) {

    override fun build(): Header {
        return DefaultHeader(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
        ).attachListeners()
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = HeaderBuilder()
    }
}
