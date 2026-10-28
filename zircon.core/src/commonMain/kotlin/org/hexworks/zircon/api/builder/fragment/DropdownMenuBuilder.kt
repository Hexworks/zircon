package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.fragment.menu.DropdownMenu
import org.hexworks.zircon.api.fragment.menu.DropdownMenuItem


@ZirconDsl
public class DropdownMenuBuilder<T : Any> : FragmentBuilder<DropdownMenu<T>> {

    public var label: String = ""
    internal var children: List<DropdownMenuItem<T>> = listOf()
    /** Dropdown menus are positioned by their menu bar; this value is only a default. */
    override var position: Position = Position.ZERO


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