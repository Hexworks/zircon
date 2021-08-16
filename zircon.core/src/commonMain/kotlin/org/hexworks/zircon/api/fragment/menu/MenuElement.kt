package org.hexworks.zircon.api.fragment.menu

import org.hexworks.zircon.api.Beta

@Beta
sealed class MenuElement(
    val label: String,
)

// TODO: later we can have a MenuBarItem to add individual buttons to a MenuBar

@Beta
abstract class DropdownElement(
    label: String,
) : MenuElement(label) {
    val width: Int = label.length
}

@Beta
class DropdownMenu<T : Any> internal constructor(
    label: String,
    // TODO: later we can change this to DropdownMenu, then we can have nested menus
    val children: List<DropdownMenuItem<T>>
) : DropdownElement(label)

@Beta
class DropdownMenuItem<T : Any> internal constructor(
    label: String,
    val key: T,
) : DropdownElement(label)


