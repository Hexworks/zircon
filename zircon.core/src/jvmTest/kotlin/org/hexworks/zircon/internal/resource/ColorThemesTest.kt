package org.hexworks.zircon.internal.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.ColorTheme
import org.junit.Test

class ColorThemesTest {

    @Test
    fun shouldContainAllThemes() {
        val themeCount = ColorThemes::class.members
                .filter { it.isFinal }
                .filterNot { it.name == "newBuilder" }
                .map { accessor ->
                    assertThat(accessor.call(ColorThemes))
                            .describedAs("Theme: ${accessor.name}")
                            .isInstanceOf(ColorTheme::class.java)
                    1
                }.reduce(Int::plus)

        assertThat(themeCount).isEqualTo(ENUM_THEMES.size)
    }

    companion object {
        private val ENUM_THEMES = ColorThemeResource.values().toList()
    }
}
