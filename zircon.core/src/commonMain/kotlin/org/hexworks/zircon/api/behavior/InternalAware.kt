package org.hexworks.zircon.api.behavior

/**
 * Represents a class which has a public (external) and a private (internal) API.
 * The *public* API is meant for those who use Zircon and the *internal* API is meant
 * for those who wish to extend Zircon's features. This distinction is necessary to
 * keep the *public* API clean and easy to use.
 *
 * @param T the type of the *internal* API of this class.
 */
interface InternalAware<out T: Any> {

    /**
     * Exposes this object's *internal* API.
     */
    fun asInternal(): T
}