package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.TextCharacter

data class MapSegment(val data: Array<Array<Array<TextCharacter>>>) {

    fun getColumnCount() = data.first().first().size

    fun getRowCount() = data.first().size

    fun getLayerCount() = data.size

}