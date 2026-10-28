package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Component


public interface TableColumn<T : Any, V : Any, C : Component> {

    public val name: String
    public val width: Int
    public val header: Component

    public fun renderCellFor(model: T): C
}