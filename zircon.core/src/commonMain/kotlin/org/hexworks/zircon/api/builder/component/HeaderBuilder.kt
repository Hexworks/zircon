package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultHeader
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class HeaderBuilder : ComponentWithTextBuilder<Header>(
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
}


/**
 * Creates a new [Header] using the component builder DSL and returns it.
 */
fun buildHeader(init: HeaderBuilder.() -> Unit): Header =
    HeaderBuilder().apply(init).build()

/**
 * Creates a new [Header] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Header].
 */
fun <T : BaseContainerBuilder<*>> T.header(
    init: HeaderBuilder.() -> Unit
): Header = buildChildFor(this, HeaderBuilder(), init)
