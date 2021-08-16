package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.events.api.CallbackResult
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.MenuBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.fragment.menu.DropdownMenu
import org.hexworks.zircon.api.fragment.menu.MenuSelection
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultMenuBar
import kotlin.jvm.JvmStatic

@Beta
@ZirconDsl
class MenuBarBuilder<T : Any> private constructor(
    var screen: Screen? = null,
    var menuElements: List<DropdownMenu<T>> = listOf(),
    var theme: ColorTheme = ColorTheme.unknown(),
    var tileset: TilesetResource = TilesetResource.unknown(),
    var position: Position = Position.zero(),
    var width: Int = -1,
    var spacing: Int = 1,
    var onMenuItemSelected: ((menuSelection: MenuSelection<T>) -> CallbackResult)? = null
) : FragmentBuilder<MenuBar<T>, MenuBarBuilder<T>>, Builder<MenuBar<T>> {

    override fun build(): MenuBar<T> {
        require(menuElements.isNotEmpty()) {
            "Cannot build a MenuBar without menu elements"
        }
        require(theme.isNotUnknown) {
            "You must set a theme for a MenuBar"
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

    override fun createCopy() = MenuBarBuilder(
        screen = screen,
        menuElements = menuElements,
        theme = theme,
        tileset = tileset,
        spacing = spacing
    )

    override fun withPosition(position: Position) = also {
        this.position = position
    }

    private fun <T : Any> List<DropdownMenu<T>>.minSize(spacing: Int) = Size.create(
        width = map { it.width }.fold(0, Int::plus) + (size - 1) * spacing,
        height = 1
    )

    companion object {

        /**
         * Creates a builder to configure and build a [MenuBar].
         * @param T the type of the item(s) that can be selected
         */
        @JvmStatic
        fun <T : Any> newBuilder(): MenuBarBuilder<T> = MenuBarBuilder()
    }

}
