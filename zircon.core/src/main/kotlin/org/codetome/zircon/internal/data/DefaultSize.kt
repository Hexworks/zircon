package org.codetome.zircon.internal.data

import org.codetome.zircon.api.data.Size

data class DefaultSize(override val xLength: Int,
                   override val yLength: Int) : Size {
    init {
        require(xLength >= 0) {
            "Size.xLength cannot be less than 0!"
        }
        require(yLength >= 0) {
            "Size.yLength cannot be less than 0!"
        }
    }
}
