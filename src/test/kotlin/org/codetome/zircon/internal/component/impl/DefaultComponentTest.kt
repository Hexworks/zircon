package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.codetome.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DefaultComponentTest {

    lateinit var target: DefaultComponent

    @Before
    fun setUp() {
        target = object : DefaultComponent(
                initialSize = SIZE,
                position = POSITION,
                componentStyles = STYLES,
                wrappers = WRAPPERS) {
            override fun applyTheme(colorTheme: ColorTheme) {
                TODO("not implemented")
            }

            override fun acceptsFocus(): Boolean {
                TODO("not implemented")
            }

            override fun giveFocus(input: Optional<Input>): Boolean {
                TODO("not implemented")
            }

            override fun takeFocus(input: Optional<Input>) {
                TODO("not implemented")
            }
        }
    }

    @Test
    fun shouldProperlyApplyStylesOnInit() {
        assertThat(target.getComponentStyles().getCurrentStyle())
                .isEqualTo(STYLES.getCurrentStyle())
    }

    @Test
    fun shouldProperlyApplyStylesOnMouseOver() {
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        EventBus.emit(EventType.MouseOver(target.getId()))

        val targetChar = target.getDrawSurface().getCharacterAt(Position.DEFAULT_POSITION).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(MOUSE_OVER_STYLE.getBackgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(MOUSE_OVER_STYLE.getForegroundColor())
        assertThat(componentChanged.get()).isTrue()
    }

    @Test
    fun shouldProperlyApplyStylesOnMouseOut() {
        EventBus.emit(EventType.MouseOver(target.getId()))
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentChange, {
            componentChanged.set(true)
        })

        EventBus.emit(EventType.MouseOut(target.getId()))

        val targetChar = target.getDrawSurface().getCharacterAt(Position.DEFAULT_POSITION).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(DEFAULT_STYLE.getBackgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(DEFAULT_STYLE.getForegroundColor())
        assertThat(componentChanged.get()).isTrue()
    }

    companion object {
        val SIZE = Size.of(4, 4)
        val POSITION = Position.of(2, 3)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.BLUE)
                .foregroundColor(ANSITextColor.RED)
                .build()
        val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.GREEN)
                .foregroundColor(ANSITextColor.YELLOW)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.MAGENTA)
                .foregroundColor(ANSITextColor.BLUE)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.YELLOW)
                .foregroundColor(ANSITextColor.CYAN)
                .build()
        val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.RED)
                .foregroundColor(ANSITextColor.CYAN)
                .build()
        val STYLES = ComponentStylesBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .activeStyle(ACTIVE_STYLE)
                .disabledStyle(DISABLED_STYLE)
                .focusedStyle(FOCUSED_STYLE)
                .mouseOverStyle(MOUSE_OVER_STYLE)
                .build()
        val WRAPPERS = listOf(
                ShadowWrappingStrategy(),
                BorderWrappingStrategy(Modifiers.BORDER.of()))
    }
}