package org.codetome.zircon.api.font

/**
 * Represents the texture which is used to represent
 * characters by a given [Font].
 */
interface FontTextureRegion<T> {

    fun getBackend(): T
}