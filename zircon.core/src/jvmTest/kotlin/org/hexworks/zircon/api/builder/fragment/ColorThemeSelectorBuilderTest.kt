package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.resource.ColorThemeResource
import org.junit.Test

class ColorThemeSelectorBuilderTest {

    @Test
    fun `all themes have to be present`() {
        val theme = ColorThemes.amigaOs()
        val testComponent = Components.label().withText("Hello World").withColorTheme(theme).build()

        val actualThemesInSelector: List<ColorTheme> = Fragments.colorThemeSelector(testComponent.size.width, theme)
                .withThemeables(testComponent)
                .build().values
        assertThat(actualThemesInSelector)
                .hasSize(ColorThemeResource.values().size)

        assertThat(actualThemesInSelector)
                .containsExactlyElementsOf(
                        ColorThemeResource
                                .values()
                                .sortedBy { it.name }
                                .map { it.getTheme() })

        assertThat(actualThemesInSelector).contains(ColorThemes.defaultTheme())
    }

}
