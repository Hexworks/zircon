package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.jvm.JvmStatic

/**
 * Builder for a [Selector] to change the theme of multiple [ColorThemeOverride]s or [Group]s at runtime.
 */
@Deprecated("This class is redundant, use a regular Selector instead")
@Suppress("MemberVisibilityCanBePrivate")
class ColorThemeSelectorBuilder(
    width: Int,
    theme: ColorTheme,
    valuesProperty: ListProperty<ColorTheme> = ColorThemeResource.values()
        .sortedBy { it.name }
        .map { it.getTheme() }.toProperty()
) : SelectorBuilder<ColorTheme>(
    width = width,
    valuesProperty = valuesProperty
) {

    private var themeOverrides = listOf<ColorThemeOverride>()
    private var groups = listOf<Group<out Component>>()

    init {
        withDefaultSelected(theme)
    }

    /**
     * Sets the given [themeOverrides] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withThemeOverrides(vararg themeOverrides: ColorThemeOverride) = also {
        this.themeOverrides = themeOverrides.toList()
    }

    /**
     * Sets the given [groups] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withGroups(vararg groups: Group<out Component>) = also {
        this.groups = groups.toList()
    }

    override fun build(): Selector<ColorTheme> = super.build().apply {
        themeOverrides.forEach {
            it.themeProperty.updateFrom(selectedValue)
        }
        groups.forEach {
            it.themeProperty.updateFrom(selectedValue)
        }
    }

    override fun createCopy(): Builder<Selector<ColorTheme>> = super.createCopy().apply {
        withThemeOverrides(*themeOverrides.toTypedArray())
        withGroups(*groups.toTypedArray())
    }

    companion object {

        /**
         * Creates a new [ColorThemeSelectorBuilder] to build [Selector]s for [ColorTheme]s.
         */
        @JvmStatic
        fun newBuilder(width: Int, theme: ColorTheme) = ColorThemeSelectorBuilder(width, theme)
    }
}
