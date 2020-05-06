package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.internal.resource.ColorThemeResource
import org.junit.Test

class ColorthemeSelectorBuilderTest {
    @Test
    fun `all themes have to be present`() {
        val someColortheme = ColorThemes.amigaOs()
        val testComponent = Components.label().withText("Hello World").withColorTheme(someColortheme).build()

        val actualThemesInMultiselect = Fragments.colorThemeSelector(testComponent.size.width, testComponent).build().values
        assertThat(actualThemesInMultiselect)
                .hasSize(ColorThemeResource.values().size)

        assertThat(actualThemesInMultiselect)
                .containsExactlyElementsOf(
                        ColorThemeResource
                                .values()
                                .sortedBy { it.name })

        assertThat(actualThemesInMultiselect.map { it.getTheme() })
                .contains(ColorThemes.defaultTheme())
    }

}