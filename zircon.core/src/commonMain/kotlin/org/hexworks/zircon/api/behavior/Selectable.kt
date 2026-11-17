package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultSelectable

/**
 * Represents an object that supports the notion of selection.
 */
public interface Selectable {

    public var isSelected: Boolean

    /**
     * A [Property] that wraps the [isSelected] value and offers data binding and
     * observability features.
     *
     * @see Property
     */
    public val selectedProperty: Property<Boolean>

    /**
     * Tells this [Selectable] apart from the others in a group, like an
     * [org.hexworks.zircon.api.component.ExclusiveSelectionGroup]. Unique within a group.
     */
    public val key: String

    public companion object {

        /**
         * Creates a new [Selectable] with the default [initialSelected] value of `false`.
         */
        public fun create(initialSelected: Boolean = false, key: String = ""): Selectable =
            create(initialSelected.toProperty(), key)

        public fun create(selectedProperty: Property<Boolean>, key: String = ""): Selectable =
            DefaultSelectable(selectedProperty, key)

        /**
         * The key a [Selectable] gets from its text when none is set: the text lower cased,
         * runs of anything but letters and digits turned into one `-`, e.g. `Go to the bar`
         * becomes `go-to-the-bar`. Empty when the text has no letters or digits.
         */
        public fun keyFrom(text: String): String = buildString {
            var dash = false
            for (c in text.lowercase()) {
                if (c.isLetterOrDigit()) {
                    append(c)
                    dash = false
                } else if (!dash && isNotEmpty()) {
                    append('-')
                    dash = true
                }
            }
        }.trimEnd('-')
    }
}
