package org.hexworks.zircon.api

import org.hexworks.cobalt.datatypes.Maybe
import kotlin.jvm.JvmStatic

// TODO: deprecate this once Arrow becomes multiplatform and use it instead of Maybes
object Maybes {

    /**
     * Creates an empty [Maybe].
     */
    @Deprecated("This function is deprecated, use the orNull constructs instead")
    @JvmStatic
    fun <T> empty(): Maybe<T> {
        return Maybe.empty()
    }

    /**
     * Creates a [Maybe] of a non-nullable value.
     */
    @Deprecated("This function is deprecated, use the orNull constructs instead")
    @JvmStatic
    fun <T> of(value: T): Maybe<T> {
        return Maybe.of(value)
    }

    /**
     * Creates a [Maybe] of a nullable value.
     */
    @Deprecated("This function is deprecated, use the orNull constructs instead")
    @JvmStatic
    fun <T> ofNullable(value: T?): Maybe<T> {
        return Maybe.ofNullable(value)
    }
}
