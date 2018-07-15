package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.component.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.graphics.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.platform.factory.TextColorFactory
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultRadioButtonTest {

    lateinit var target: DefaultRadioButton

    @Before
    fun setUp() {
        target = DefaultRadioButton(
                text = TEXT,
                wrappers = ThreadSafeQueueFactory.create(),
                width = WIDTH,
                position = POSITION,
                componentStyleSet = COMPONENT_STYLES,
                initialFont = FontSettings.NO_FONT)
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = target.getDrawSurface()
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getCharacterAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TextCharacterBuilder.newBuilder()
                            .character(char)
                            .styleSet(DEFAULT_STYLE)
                            .build())
        }
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.getText()).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.getComponentStyles()
        assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
        assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(EXPECTED_FOCUSED_STYLE)
        assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(EXPECTED_ACTIVE_STYLE)
        assertThat(styles.getStyleFor(ComponentState.DISABLED))
                .isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.applyColorTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyColorTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        target.takeFocus()

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlySelect() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }
        target.applyColorTheme(THEME)
        target.select()

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.isSelected()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    @Test
    fun shouldSelectOnlyWhenNotAlreadySelected() {
        target.select()
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }
        target.select()

        assertThat(componentChanged.get()).isFalse()
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }
        target.applyColorTheme(THEME)
        target.select()
        target.removeSelection()

        assertThat(getButtonChar()).isEqualTo(' ')
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.isSelected()).isFalse()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldDeselectOnlyWhenSelected() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }
        target.removeSelection()

        assertThat(componentChanged.get()).isFalse()
    }

    private fun getButtonChar() = target.getDrawSurface().getCharacterAt(Position.create(1, 0)).get().getCharacter()

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val WIDTH = 20
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.RED)
                .foregroundColor(ANSITextColor.GREEN)
                .modifiers(Modifiers.CROSSED_OUT)
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getAccentColor())
                .backgroundColor(TextColorFactory.transparent())
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getDarkForegroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()
    }
}
