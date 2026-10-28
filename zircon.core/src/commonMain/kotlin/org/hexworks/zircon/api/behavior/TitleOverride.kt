package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleOverride

/**
 * Represents an object that has a visual [title] that can be changed.
 */
public interface TitleOverride : HasTitle {

    /**
     * The (mutable) title.
     */
    override var title: String

    /**
     * A [Property] that wraps the [title] and offers data binding and
     * observability features.
     *
     * @see Property
     */
    public val titleProperty: Property<String>

    public companion object {

        public fun create(initialTitle: String = ""): TitleOverride = DefaultTitleOverride(initialTitle.toProperty())

        /**
         * Creates a new [TitleOverride] object with a default title value of `""`.
         */
        public fun create(initialTitle: Property<String> = "".toProperty()): TitleOverride = DefaultTitleOverride(initialTitle)
    }
}
