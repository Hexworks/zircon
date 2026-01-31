package org.hexworks.zircon.api.fragment.menu


sealed class MenuElement(
    val label: String,
)

// TODO: later we can have a MenuBarItem to add individual buttons to a MenuBar


abstract class DropdownElement(
    label: String,
) : MenuElement(label) {
    val width: Int = label.length
}


class DropdownMenu<T : Any> internal constructor(
    label: String,
    // TODO: later we can change this to DropdownMenu, then we can have nested menus
    val children: List<DropdownMenuItem<T>>
) : DropdownElement(label)


class DropdownMenuItem<T : Any> internal constructor(
    label: String,
    val key: T,
) : DropdownElement(label)


