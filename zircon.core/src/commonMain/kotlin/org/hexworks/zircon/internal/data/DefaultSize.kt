package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseSize

data class DefaultSize(
    override val width: Int,
    override val height: Int
) : BaseSize() {

    override fun toString() = "(w=${width},h=$height)"

    init {
        require(width >= 0 && height >= 0) {
            "Can't create a Size with negative width ($width) or height ($height)."
        }
    }
}
