package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.ColorThemeRepository
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DefaultRadioButtonTest {

    lateinit var target: DefaultRadioButton

    @Before
    fun setUp() {
        target = DefaultRadioButton(
                text = TEXT,
                wrappers = LinkedList(),
                width = WIDTH,
                position = POSITION,
                componentStyles = COMPONENT_STYLES)
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = target.getDrawSurface()
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getCharacterAt(Position.of(i + offset, 0)).get())
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
        target.applyTheme(THEME)
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
        target.applyTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.applyTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        target.takeFocus()

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyHandleMousePress() {
        target.applyTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        EventBus.emit(
                type = EventType.MousePressed(target.getId()),
                data = MouseAction(MouseActionType.MOUSE_PRESSED, 1, Position.DEFAULT_POSITION))

        assertThat(getButtonChar()).isEqualTo('o')
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_ACTIVE_STYLE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.applyTheme(THEME)
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        EventBus.emit(
                type = EventType.MouseReleased(target.getId()),
                data = MouseAction(MouseActionType.MOUSE_PRESSED, 1, Position.DEFAULT_POSITION))

        assertThat(componentChanged.get()).isTrue()
        assertThat(target.isSelected()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    @Test
    fun shouldProperlySelect() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })
        target.applyTheme(THEME)
        target.select()

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.isSelected()).isTrue()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_MOUSE_OVER_STYLE)
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })
        target.applyTheme(THEME)
        target.select()
        target.removeSelection()

        assertThat(getButtonChar()).isEqualTo(' ')
        assertThat(componentChanged.get()).isTrue()
        assertThat(target.isSelected()).isFalse()
        assertThat(target.getComponentStyles().getCurrentStyle()).isEqualTo(EXPECTED_DEFAULT_STYLE)
    }

    private fun getButtonChar() = target.getDrawSurface().getCharacterAt(Position.of(1, 0)).get().getCharacter()

    companion object {
        val THEME = ColorThemeRepository.ADRIFT_IN_DREAMS.getTheme()
        val TEXT = "Button text"
        val WIDTH = 20
        val POSITION = Position.of(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.RED)
                .foregroundColor(ANSITextColor.GREEN)
                .modifier(Modifiers.CROSSED_OUT)
                .build()
        val COMPONENT_STYLES = ComponentStylesBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()

        val EXPECTED_DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getAccentColor())
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .build()

        val EXPECTED_MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()

        val EXPECTED_ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightBackgroundColor())
                .backgroundColor(THEME.getAccentColor())
                .build()
    }
}