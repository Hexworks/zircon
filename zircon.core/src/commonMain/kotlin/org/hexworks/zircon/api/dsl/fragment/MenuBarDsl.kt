package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.zircon.api.builder.fragment.DropdownMenuBuilder
import org.hexworks.zircon.api.builder.fragment.DropdownMenuItemBuilder
import org.hexworks.zircon.api.builder.fragment.MenuBarBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.MenuBar

/**
 * Creates a new [MenuBar] using the fragment builder DSL and returns it.
 */
fun <T : Any> buildMenuBar(
    init: MenuBarBuilder<T>.() -> Unit
): MenuBar<T> = MenuBarBuilder.newBuilder<T>().apply(init).build()

/**
 * Creates a new [MenuBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [MenuBar].
 */

fun <T : Any> AnyContainerBuilder.menuBar(
    init: MenuBarBuilder<T>.() -> Unit
): MenuBar<T> = buildFragmentFor(this, MenuBarBuilder.newBuilder(), init)


fun <T : Any> MenuBarBuilder<T>.dropdownMenu(init: DropdownMenuBuilder<T>.() -> Unit) {
    val builder = DropdownMenuBuilder.newBuilder<T>()
    init(builder)
    this.menuElements = menuElements + builder.build()
}


fun <T : Any> DropdownMenuBuilder<T>.menuItem(init: DropdownMenuItemBuilder<T>.() -> Unit) {
    val builder = DropdownMenuItemBuilder.newBuilder<T>()
    init(builder)
    children = children + builder.build()
}

