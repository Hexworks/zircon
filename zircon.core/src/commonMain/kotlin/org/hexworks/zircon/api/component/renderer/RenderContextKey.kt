package org.hexworks.zircon.api.component.renderer

/**
 * This simple interface is used to set and retrieve custom properties on [ComponentRenderContext]
 * in a typesafe way.
 */
interface RenderContextKey<T> {
    /**
     * This function will be called in case there is no default value for this key.
     */
    fun getDefaultValue(): T
}
