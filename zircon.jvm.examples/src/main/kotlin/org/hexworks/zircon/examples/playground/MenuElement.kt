package org.hexworks.zircon.examples.playground

sealed class MenuElement(
    val label: String
)

class MenuBarItem(
    label: String,
    val children: List<MenuElement>
) : MenuElement(label)

class MenuItem(
    val key: String,
    label: String
) : MenuElement(label)


