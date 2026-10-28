package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.listItem
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.graphics.TextWrap
import kotlin.math.ceil

public fun LogArea.addParagraph(text: String) {
    val width = minOf(this.contentSize.width, text.length)
    val height = ceil(text.length.toDouble().div(width)).toInt()

    addRow(paragraph {
        +text
        preferredSize = Size(width, height)
        textWrap = TextWrap.WORD_WRAP
    })
}

public fun LogArea.addHeader(text: String) {
    addRow(header {
        +text
    })
}

public fun LogArea.addListItem(text: String) {
    addRow(listItem { +text })
}
