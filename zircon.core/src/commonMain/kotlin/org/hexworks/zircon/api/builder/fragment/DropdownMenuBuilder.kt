package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.fragment.menu.DropdownMenu
import org.hexworks.zircon.api.fragment.menu.DropdownMenuItem
import org.hexworks.zircon.internal.dsl.ZirconDsl


@ZirconDsl
class DropdownMenuBuilder<T : Any> : FragmentBuilder<DropdownMenu<T>> {

    var label: String = ""
    internal var children: List<DropdownMenuItem<T>> = listOf()
    override var position: Position
        get() = TODO("Not yet implemented")
        set(value) {}


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
}