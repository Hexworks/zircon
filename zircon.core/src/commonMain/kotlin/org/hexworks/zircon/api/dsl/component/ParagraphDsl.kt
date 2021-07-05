package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Paragraph] using the component builder DSL and returns it.
 */
fun buildParagraph(init: ParagraphBuilder.() -> Unit): Paragraph =
        ParagraphBuilder().apply(init).build()

/**
 * Creates a new [Paragraph] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Paragraph].
 */
fun <T : BaseContainerBuilder<*, *>> T.paragraph(
        init: ParagraphBuilder.() -> Unit
): Paragraph = buildChildFor(this, ParagraphBuilder(), init)
