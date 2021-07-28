package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.fragment.menu.DropdownMenuItem
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class DropdownMenuItemBuilder<T : Any>(
    var label: String = "",
    var key: T? = null
) : Builder<DropdownMenuItem<T>> {

    override fun createCopy() = DropdownMenuItemBuilder<T>(
        label = label,
        key = key
    )

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