package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.fragment.menu.DropdownMenuItem


@ZirconDsl
class DropdownMenuItemBuilder<T : Any> : Builder<DropdownMenuItem<T>> {

    var label: String = ""
    var key: T? = null

    override fun build(): DropdownMenuItem<T> {
        require(label.isNotEmpty()) {
            "A dropdown menu must have a label"
        }
        return DropdownMenuItem(
            label = label,
            key = key ?: error("A dropdown menu must have a key")
        )
    }
}
