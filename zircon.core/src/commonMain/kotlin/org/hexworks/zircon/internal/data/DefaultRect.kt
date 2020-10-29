package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.base.BaseRect

class DefaultRect(
        override val position: Position,
        override val size: Size
) : BaseRect() {

    override val rect: Rect = this

    override fun toString(): String {
        return "${this::class.simpleName}(position=$position,size=$size)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultRect

        if (position != other.position) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }

}
