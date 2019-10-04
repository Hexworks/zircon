package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.TitleHolder

class DefaultTitleHolder(initialTitle: String) : TitleHolder {

    override val titleProperty = createPropertyFrom(initialTitle)
    override var title: String by titleProperty.asDelegate()
}
