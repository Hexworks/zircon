package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.builder.component.buildHeader
import org.hexworks.zircon.api.builder.component.buildListItem
import org.hexworks.zircon.api.builder.component.buildParagraph
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.graphics.TextWrap
import kotlin.math.ceil

fun LogArea.addParagraph(text: String) {
    val width = minOf(this.contentSize.width, text.length)
    val height = ceil(text.length.toDouble().div(width)).toInt()

    addRow(buildParagraph {
        +text
        withPreferredSize {
            this.width = width
            this.height = height
        }
        textWrap = TextWrap.WORD_WRAP
    })
}

fun LogArea.addHeader(text: String) {
    addRow(buildHeader {
        +text
    })
}

fun LogArea.addListItem(text: String) {
    addRow(buildListItem { +text })
}
