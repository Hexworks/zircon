package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.ListItemBuilder
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.builder.component.TextBoxBuilder
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.Header

/**
 * Creates a new [TextBox] using the component builder DSL and returns it.
 */
fun buildTextBox(
    initialContentWidth: Int,
    init: TextBoxBuilder.() -> Unit
): TextBox = TextBoxBuilder.newBuilder(initialContentWidth).apply(init).build()

/**
 * Creates a new [TextBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TextBox].
 */
fun <T : BaseContainerBuilder<*, *>> T.textBox(
    initialWidth: Int,
    init: TextBoxBuilder.() -> Unit
): TextBox = buildChildFor(this, TextBoxBuilder.newBuilder(initialWidth), init)

/**
 * Creates a new [Paragraph] using the component builder DSL, adds it to the
 * receiver [TextBoxBuilder].
 */
fun TextBoxBuilder.paragraph(
    withNewLine: Boolean = true,
    init: ParagraphBuilder.() -> Unit
) = addParagraph(ParagraphBuilder().apply(init).build(), withNewLine)

/**
 * Creates a new [Header] using the component builder DSL, adds it to the
 * receiver [TextBoxBuilder].
 */
fun TextBoxBuilder.header(
    withNewLine: Boolean = true,
    init: HeaderBuilder.() -> Unit
) = addHeader(HeaderBuilder().apply(init).build(), withNewLine)

/**
 * Creates a new [ListItem] using the component builder DSL, adds it to the
 * receiver [TextBoxBuilder].
 */
fun TextBoxBuilder.listItem(
    withNewLine: Boolean = true,
    init: ListItemBuilder.() -> Unit
) = addListItem(ListItemBuilder().apply(init).build(), withNewLine)
