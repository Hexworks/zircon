package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.component.Paragraph

fun paragraph(init: ParagraphBuilder.() -> Unit): Paragraph =
    ParagraphBuilder().apply(init).build()