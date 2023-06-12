package org.hexworks.zircon.api.builder

/**
 * Simple builder pattern interface for [build]ing instances of type [T].
 * All builders have sensible defaults. If there are no sensible defaults a [Builder]
 * should provide a factory method that ensures that mandatory parameters are passed
 * to the [Builder] when it is created. This ensures that exceptions are not thrown from
 * [build] if its state is inconsistent.
 *
 * All configuration properties in a [Builder] should have a fluent setter like this:
 *
 * ```kotlin
 * fun withFocusNext(focusNext: KeyboardEventMatcher) = also {
 *     this.focusNext = focusNext
 * }
 * ```
 */
interface Builder<out T> {

    /**
     * Builds an object of type [T].
     */
    fun build(): T

}
