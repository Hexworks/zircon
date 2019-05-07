package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleHolder

interface TitleHolder {

    var title: String
    val titleProperty: Property<String>

    fun onTitleChanged(fn: ChangeListener<String>): Subscription {
        return titleProperty.onChange(fn::onChange)
    }

    companion object {

        fun create(initialTitle: String = ""): TitleHolder = DefaultTitleHolder(initialTitle)
    }
}
