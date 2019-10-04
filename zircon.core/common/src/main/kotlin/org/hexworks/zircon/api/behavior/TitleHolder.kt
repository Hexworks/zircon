package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleHolder

/**
 * Represents an object which has a [title].
 */
interface TitleHolder {

    var title: String
    val titleProperty: Property<String>

    fun onTitleChanged(fn: (ChangeEvent<String>) -> Unit): Subscription {
        return titleProperty.onChange(fn)
    }

    companion object {

        fun create(initialTitle: String = ""): TitleHolder = DefaultTitleHolder(initialTitle)
    }
}
