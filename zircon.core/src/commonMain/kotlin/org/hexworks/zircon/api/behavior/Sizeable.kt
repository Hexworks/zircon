package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Size

/**
 * Represents an object which has bounds (eg: [size])
 */
@Deprecated("Sizeable was renamed to HasSize, please use that instead. Sizeable will be removed in the next release")
interface Sizeable {

    val size: Size

    val width: Int
        get() = size.width

    val height: Int
        get() = size.height
}
