package org.codetome.zircon.api.component

import org.codetome.zircon.api.resource.ColorThemeResource
import org.junit.Test

class ColorThemeRepositoryTest {

    @Test
    fun shouldBeAbleToCreateAllThemes() {
        ColorThemeResource.values().forEach {
            it.getTheme()
        }
    }
}