package org.hexworks.zircon.api.component

import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.test.Test

class ColorThemeRepositoryTest {

    @Test
    fun shouldBeAbleToCreateAllThemes() {
        ColorThemeResource.values().forEach {
            it.getTheme()
        }
    }
}
