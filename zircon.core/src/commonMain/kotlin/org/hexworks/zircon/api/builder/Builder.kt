package org.hexworks.zircon.api.builder

/**
 * A [Builder] collects configuration as plain `var` properties and creates a [T] from
 * them in [build]. Builders are used through the DSL functions next to them, for example
 * `button { text = "OK" }`, and every property has a sensible default so [build] never
 * throws for an untouched builder.
 */
public interface Builder<out T> {

    /**
     * Builds an object of type [T].
     */
    public fun build(): T

}
