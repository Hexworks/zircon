package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TitleOverride

class DefaultTitleOverride(
    override val titleProperty: Property<String>
) : TitleOverride {
    override var title: String by titleProperty.asDelegate()
}
