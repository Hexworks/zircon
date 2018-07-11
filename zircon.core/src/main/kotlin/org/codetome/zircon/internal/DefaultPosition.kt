package org.codetome.zircon.internal

import org.codetome.zircon.api.Position

data class DefaultPosition(
        override val x: Int,
        override val y: Int) : Position {
    init {
        require(x >= 0 && y >= 0) {
            "A position must have a x and a y which is greater than or equal to 0!"
        }
    }
}
