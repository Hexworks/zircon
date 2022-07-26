package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.fragment.menu.DropdownMenu
import org.hexworks.zircon.api.fragment.menu.DropdownMenuItem
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic


@ZirconDsl
class DropdownMenuBuilder<T : Any> private constructor(
    var label: String = "",
    internal var children: List<DropdownMenuItem<T>> = listOf()
) : Builder<DropdownMenu<T>> {

    override fun createCopy() = DropdownMenuBuilder(
        label = label,
        children = children
    )

    override fun build(): DropdownMenu<T> {
        require(label.isNotEmpty()) {
            "A dropdown menu must have a label"
        }
        require(children.isNotEmpty()) {
            "A dropdown menu must have children"
        }
        return DropdownMenu(
            label = label,
            children = children
        )
    }

    companion object {

        @JvmStatic
        fun <T: Any> newBuilder() = DropdownMenuBuilder<T>()
    }


}
