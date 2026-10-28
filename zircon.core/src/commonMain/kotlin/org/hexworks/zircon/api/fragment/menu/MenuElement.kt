package org.hexworks.zircon.api.fragment.menu


public sealed class MenuElement(
    public val label: String,
)

// TODO: later we can have a MenuBarItem to add individual buttons to a MenuBar


public abstract class DropdownElement(
    label: String,
) : MenuElement(label) {
    public val width: Int = label.length
}


public class DropdownMenu<T : Any> internal constructor(
    label: String,
    // TODO: later we can change this to DropdownMenu, then we can have nested menus
    public val children: List<DropdownMenuItem<T>>
) : DropdownElement(label)


public class DropdownMenuItem<T : Any> internal constructor(
    label: String,
    public val key: T,
) : DropdownElement(label)


