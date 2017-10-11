package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.builder.LabelBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.codetome.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.impl.FontSettings
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultContainerTest {

    lateinit var target: DefaultContainer

    @Before
    fun setUp() {
        target = DefaultContainer(
                initialSize = SIZE,
                position = POSITION,
                componentStyles = STYLES,
                wrappers = WRAPPERS,
                initialFont = GOOD_FONT)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        target.addComponent(LabelBuilder.newBuilder()
                .text("foo")
                .font(BAD_FONT)
                .build())
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIntersectsWithAnother() {
        val pos = Position.of(1, 1)
        val comp = LabelBuilder.newBuilder().position(pos).text("text").build()
        val otherComp = LabelBuilder.newBuilder().position(pos + pos).text("text").build()
        target.addComponent(comp)
        target.addComponent(otherComp)
    }

    @Test
    fun shouldSetCurrentFontToAddedComponentWithNoFont() {
        val comp = LabelBuilder.newBuilder().text("foo").build()
        target.addComponent(comp)
        assertThat(comp.getCurrentFont().getId()).isEqualTo(GOOD_FONT.getId())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIsBiggerThanTheContainer() {
        target.addComponent(PanelBuilder.newBuilder()
                .size(SIZE + SIZE)
                .build())
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    @Test
    fun shouldProperlyRemoveComponentFromSelf() {
        val comp = LabelBuilder.newBuilder()
                .text("x")
                .position(Position.DEFAULT_POSITION)
                .build()
        target.addComponent(comp)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentRemoval, {
            removalHappened.set(true)
        })

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    @Test
    fun shouldProperlyRemoveComponentFromChild() {
        val comp = LabelBuilder.newBuilder()
                .text("x")
                .position(Position.DEFAULT_POSITION)
                .build()
        val panel = PanelBuilder.newBuilder()
                .size(SIZE - Size.ONE)
                .position(Position.DEFAULT_POSITION).build()
        panel.addComponent(comp)
        target.addComponent(panel)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe(EventType.ComponentRemoval, {
            removalHappened.set(true)
        })

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    companion object {
        val GOOD_FONT = CP437TilesetResource.AESOMATICA_16X16.toFont()
        val BAD_FONT = CP437TilesetResource.BISASAM_20X20.toFont()
        val SIZE = Size.of(4, 4)
        val POSITION = Position.of(2, 3)
        val NEW_POSITION = Position.of(6, 7)
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
                BorderWrappingStrategy(Modifiers.BORDER.create()))
    }
}