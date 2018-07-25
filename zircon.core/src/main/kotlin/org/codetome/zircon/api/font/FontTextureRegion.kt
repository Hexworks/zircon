package org.codetome.zircon.api.font

import org.codetome.zircon.api.behavior.Cacheable

/**
 * Represents the texture which is used to draw
 * characters for a given [Font].
 */
interface FontTextureRegion<out T> : Cacheable {

    fun getBackend(): T
}
