package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.component.TextArea

fun textArea(init: TextAreaBuilder.() -> Unit): TextArea =
    TextAreaBuilder().apply(init).build()