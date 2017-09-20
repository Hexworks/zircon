package org.codetome.zircon.api.component

import org.junit.Test

class ColorThemeRepositoryTest {

    @Test
    fun shouldBeAbleToCreateAllThemes() {
        ColorThemeRepository.values().forEach {
            it.getTheme()
        }
    }
}