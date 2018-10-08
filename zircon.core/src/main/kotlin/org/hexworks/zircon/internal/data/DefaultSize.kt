package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Size

data class DefaultSize(override val width: Int,
                       override val height: Int) : Size {
    init {
        require(width >= 0) {
            "Size.xLength cannot be less than 0!"
        }
        require(height >= 0) {
            "Size.yLength cannot be less than 0!"
        }
    }
}
