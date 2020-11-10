package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleOverride
import kotlin.jvm.JvmStatic

/**
 * Represents an object that has a visual [title] that can be changed.
 */
// TODO: mention in the release notes that TitleHolder + TitleOverride was created
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface TitleOverride : TitleHolder {

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
    override val titleProperty: Property<String>

    companion object {

        /**
         * Creates a new [TitleOverride] object with a default title value of `""`.
         */
        @JvmStatic
        fun create(initialTitle: String = ""): TitleOverride = DefaultTitleOverride(initialTitle)
    }
}
