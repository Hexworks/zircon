package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.jvm.JvmStatic

// constructor could be private but java can not handle private vararg constructors
class ColorThemeSelectorBuilder(
        width: Int,
        vararg componentsToUpdate: Themeable
) : FragmentBuilder<MultiSelect<ColorThemeResource>, ColorThemeSelectorBuilder> {

    companion object {
        @JvmStatic
        fun newBuilder(width: Int, vararg componentsToUpdate: Themeable) = ColorThemeSelectorBuilder(width, *componentsToUpdate)
    }

    private val multiSelectBuilder: MultiSelectBuilder<ColorThemeResource>

    init {
        require(componentsToUpdate.isNotEmpty()) {
            "ColorThemeSelector needs a list of components it can update. Supplied list was empty."
        }
        val allColorThemes: List<ColorThemeResource> = ColorThemeResource
                .values()
                .sortedBy { it.name }
        multiSelectBuilder = Fragments
                .multiSelect(width, allColorThemes)
                .withCallback { _, newThemeResource -> componentsToUpdate.forEach { it.themeProperty.updateValue(newThemeResource.getTheme()) } }

        // If the fist component has a default theme, we preselect it
        allColorThemes
                .firstOrNull { it.getTheme() == componentsToUpdate.first().theme }
                ?.let { multiSelectBuilder.withDefaultSelected(it) }
    }

    override fun withPosition(position: Position): ColorThemeSelectorBuilder = also {
        multiSelectBuilder.withPosition(position)
    }

    override fun withPosition(x: Int, y: Int): ColorThemeSelectorBuilder = withPosition(Position.create(x, y))

    override fun build(): MultiSelect<ColorThemeResource> = multiSelectBuilder.build()

    override fun createCopy(): Builder<MultiSelect<ColorThemeResource>> =
            multiSelectBuilder.createCopy()
}