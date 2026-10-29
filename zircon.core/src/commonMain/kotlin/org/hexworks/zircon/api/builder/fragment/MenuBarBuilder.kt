package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.events.api.CallbackResult
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.MenuBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.fragment.menu.DropdownMenu
import org.hexworks.zircon.api.fragment.menu.MenuSelection
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.internal.fragment.impl.DefaultMenuBar


@ZirconDsl
public class MenuBarBuilder<T : Any> : FragmentBuilder<MenuBar<T>> {

    override var position: Position = Position.ZERO

    public var screen: Screen? = null
    public var menuElements: List<DropdownMenu<T>> = listOf()
    public var theme: ColorTheme = ZirconDefaults.current.theme
    public var tileset: TilesetResource = ZirconDefaults.current.tileset
    public var width: Int = -1
    public var spacing: Int = 1
    public var onMenuItemSelected: ((menuSelection: MenuSelection<T>) -> CallbackResult)? = null

    override fun build(): MenuBar<T> {
        require(menuElements.isNotEmpty()) {
            "Cannot build a MenuBar without menu elements"
        }
        val minWidth = menuElements.minSize(1).width
        val finalWidth = if (width > 0) {
            require(width >= minWidth) {
                "width ($width) is smaller that the required min width ($minWidth) to fit the menu items."
            }
            width
        } else minWidth
        return DefaultMenuBar(
            screen = screen ?: error("Cannot build a MenuBar without a Screen"),
            position = position,
            spacing = spacing,
            theme = theme,
            tileset = tileset,
            menuElements = menuElements,
            width = finalWidth,
        ).apply {
            onMenuItemSelected?.let { callback ->
                this.onMenuItemSelected(callback)
            }
        }
    }

    private fun <T : Any> List<DropdownMenu<T>>.minSize(spacing: Int) = Size(
        width = map { it.width }.fold(0, Int::plus) + (size - 1) * spacing,
        height = 1
    )
}

/**
 * Creates a new [MenuBar] using the fragment builder DSL and returns it.
 */
public fun <T : Any> menuBar(
    init: MenuBarBuilder<T>.() -> Unit
): MenuBar<T> = MenuBarBuilder<T>().apply(init).build()

/**
 * Creates a new [MenuBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [MenuBar].
 */

public fun <T : Any> AnyContainerBuilder.menuBar(
    init: MenuBarBuilder<T>.() -> Unit
): MenuBar<T> = buildFragmentFor(this, MenuBarBuilder(), init)


public fun <T : Any> MenuBarBuilder<T>.dropdownMenu(init: DropdownMenuBuilder<T>.() -> Unit) {
    val builder = DropdownMenuBuilder<T>()
    init(builder)
    this.menuElements = menuElements + builder.build()
}


public fun <T : Any> DropdownMenuBuilder<T>.menuItem(init: DropdownMenuItemBuilder<T>.() -> Unit) {
    val builder = DropdownMenuItemBuilder<T>()
    init(builder)
    children = children + builder.build()
}
