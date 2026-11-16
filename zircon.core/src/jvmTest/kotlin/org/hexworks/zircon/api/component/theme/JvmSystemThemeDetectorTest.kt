package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class JvmSystemThemeDetectorTest {

    @Test
    fun registry_dword_zero_is_dark_and_one_is_light() {
        "    AppsUseLightTheme    REG_DWORD    0x0".registryDwordToThemeMode() shouldBe ThemeMode.DARK
        "    AppsUseLightTheme    REG_DWORD    0x1".registryDwordToThemeMode() shouldBe ThemeMode.LIGHT
        "ERROR: The system was unable to find the specified registry key".registryDwordToThemeMode() shouldBe null
    }

    @Test
    fun the_jvm_detector_does_not_throw() {
        val answer = JvmSystemThemeDetector.detect()
        (answer == null || answer == ThemeMode.LIGHT || answer == ThemeMode.DARK) shouldBe true
    }
}
