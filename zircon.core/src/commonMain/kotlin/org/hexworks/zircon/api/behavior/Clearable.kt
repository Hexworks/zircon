package org.hexworks.zircon.api.behavior

/**
 * Clearing resets the [Clearable] object to its default state, that is visually empty.
 */
interface Clearable {

    /**
     * Clears this [Clearable].
     */
    fun clear()
}
