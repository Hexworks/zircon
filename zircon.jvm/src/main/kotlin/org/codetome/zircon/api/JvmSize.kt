package org.codetome.zircon.api

/**
 * Dimensions in 2D space.
 * This class is immutable and cannot change its internal state after creation.
 */
data class JvmSize(override val xLength: Int,
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
