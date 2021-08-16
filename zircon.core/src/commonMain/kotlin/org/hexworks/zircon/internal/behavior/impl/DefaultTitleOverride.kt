package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.TitleOverride

class DefaultTitleOverride(initialTitle: String) : TitleOverride {

    override val titleProperty = initialTitle.toProperty()
    override var title: String by titleProperty.asDelegate()
}
