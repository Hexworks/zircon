package org.hexworks.zircon.api.application

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.junit.Test

private object TestAppConfigKey : AppConfigKey<String>
private object TestAppConfigKey2 : AppConfigKey<String>

class AppConfigTest {

    @Test
    fun shouldProperlySetValues() {
        val target = AppConfigBuilder.newBuilder()
            .withBlinkLengthInMilliSeconds(BLINK_TIME)
            .withClipboardAvailable(HAS_CLIPBOARD)
            .withCursorBlinking(IS_BLINKING)
            .withCursorColor(CURSOR_COLOR)
            .withCursorStyle(CURSOR_STYLE)
            .build()

        assertThat(target.blinkLengthInMilliSeconds)
            .isEqualTo(BLINK_TIME)
        assertThat(target.cursorStyle)
            .isEqualTo(CURSOR_STYLE)
        assertThat(target.cursorColor)
            .isEqualTo(CURSOR_COLOR)
        assertThat(target.isCursorBlinking)
            .isEqualTo(IS_BLINKING)
        assertThat(target.isClipboardAvailable)
            .isEqualTo(HAS_CLIPBOARD)
    }

    @Test
    fun propertyUnset() {
        val appConfig = AppConfigBuilder.newBuilder().build()
        assertThat(appConfig.getOrNull(TestAppConfigKey))
            .isNull()
    }

    @Test
    fun propertySet() {
        val appConfig = AppConfigBuilder.newBuilder()
            .withProperty(TestAppConfigKey, "foo")
            .build()
        assertThat(appConfig.getOrNull(TestAppConfigKey))
            .isEqualTo("foo")
    }

    @Test
    fun propertyOverwrite() {
        val appConfig = AppConfigBuilder.newBuilder()
            .withProperty(TestAppConfigKey, "foo")
            .withProperty(TestAppConfigKey, "bar")
            .build()
        assertThat(appConfig.getOrNull(TestAppConfigKey))
            .isEqualTo("bar")
    }

    @Test
    fun propertyMultiple() {
        val appConfig = AppConfigBuilder.newBuilder()
            .withProperty(TestAppConfigKey, "foo")
            .withProperty(TestAppConfigKey2, "bar")
            .build()
        assertThat(appConfig.getOrNull(TestAppConfigKey))
            .isEqualTo("foo")
        assertThat(appConfig.getOrNull(TestAppConfigKey2))
            .isEqualTo("bar")
    }

    @Test
    fun propertyExample() {
        // Plugin API
        val key = object : AppConfigKey<Int> {} // use a real internal or private `object`, not an anonymous one!
        fun AppConfigBuilder.enableCoolFeature() = also { withProperty(key, 42) }

        // User code
        val appConfig = AppConfigBuilder.newBuilder()
            .enableCoolFeature()
            .build()

        // Plugin internals
        assertThat(appConfig.getOrNull(key))
            .isEqualTo(42)
    }

    companion object {
        val BLINK_TIME = 5L
        val CURSOR_STYLE = CursorStyle.UNDER_BAR
        val CURSOR_COLOR = ANSITileColor.GREEN
        val IS_BLINKING = true
        val HAS_CLIPBOARD = true
    }
}
