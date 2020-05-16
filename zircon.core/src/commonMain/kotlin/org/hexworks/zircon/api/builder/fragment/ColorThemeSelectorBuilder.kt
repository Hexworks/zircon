package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.internal.resource.ColorThemeResource

/**
 * Builder for a [Selector] to change the theme of multiple [Themeable]s or [Group]s at runtime.
 */
class ColorThemeSelectorBuilder(
        width: Int,
        theme: ColorTheme
) : SelectorBuilder<ColorTheme>(width, ColorThemeResource.values()
        .sortedBy { it.name }
        .map { it.getTheme() }) {

    private var themeables = listOf<Themeable>()
    private var groups = listOf<Group<out Component>>()

    init {
        withDefaultSelected(theme)
    }

    /**
     * Sets the given [themeables] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withThemeables(vararg themeables: Themeable) = also {
        this.themeables = themeables.toList()
    }

    /**
     * Sets the given [groups] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withGroups(vararg groups: Group<out Component>) = also {
        this.groups = groups.toList()
    }

    override fun build(): Selector<ColorTheme> = super.build().apply {
        themeables.forEach {
            it.themeProperty.updateFrom(selectedValue)
        }
        groups.forEach {
            it.themeProperty.updateFrom(selectedValue)
        }
    }

    override fun createCopy(): Builder<Selector<ColorTheme>> = super.createCopy().apply {
        withThemeables(*themeables.toTypedArray())
        withGroups(*groups.toTypedArray())
    }

    companion object {

        /**
         * Creates a new [ColorThemeSelectorBuilder] to build [Selector]s for [ColorTheme]s.
         */
        fun newBuilder(width: Int, theme: ColorTheme) = ColorThemeSelectorBuilder(width, theme)
    }
}
