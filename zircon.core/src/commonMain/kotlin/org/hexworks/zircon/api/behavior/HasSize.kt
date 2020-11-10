package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Size

/**
 * Represents an object which has bounds (eg: [size])
 */
interface HasSize : Sizeable {

    override val size: Size

    override val width: Int
        get() = size.width

    override val height: Int
        get() = size.height
}
