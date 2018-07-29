package org.codetome.zircon.api.behavior

/**
 * Clearing resets the [Clearable]
 * object to its default state which is visually empty.
 */
interface Clearable {

    /**
     * Clears this [Clearable].
     */
    fun clear()
}
