package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTextOverride

/**
 * Represents an object that holds (mutable) [text].
 */
interface TextOverride {

    /**
     * The (mutable) [text].
     */
    var text: String

    /**
     * A [Property] that wraps the [text] and offers data binding and
     * observability features.
     *
     * @see Property
     */
    val textProperty: Property<String>

    companion object {

        /**
         * Creates a new [TextOverride] with the default value of `""` for [text].
         */
        fun create(initialText: String = ""): TextOverride = DefaultTextOverride(initialText.toProperty())

        /**
         * Creates a new [TextOverride] from a [Property].
         */
        fun create(property: Property<String>): TextOverride = DefaultTextOverride(property)
    }

}
