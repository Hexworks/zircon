package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.builder.component.TextBoxBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.data.Position

fun textBox(
    initialContentWidth: Int,
    nextPosition: Position = Position.defaultPosition(),
    components: MutableList<Component> = mutableListOf(),
    init: TextBoxBuilder.() -> Unit
): TextBox =
    TextBoxBuilder(initialContentWidth, nextPosition, components).apply(init).build()

fun TextBoxBuilder.paragraph(withNewLine: Boolean = true, init: ParagraphBuilder.() -> Unit) =
    addParagraph(ParagraphBuilder().apply(init).build(), withNewLine)

fun TextBoxBuilder.inlineComponent(init: () -> Component) =
    addInlineComponent(init())
