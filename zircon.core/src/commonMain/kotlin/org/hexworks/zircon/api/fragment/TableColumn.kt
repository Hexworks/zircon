package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Component


interface TableColumn<T : Any, V : Any, C : Component> {

    val name: String
    val width: Int
    val header: Component

    fun renderCellFor(model: T): C
}