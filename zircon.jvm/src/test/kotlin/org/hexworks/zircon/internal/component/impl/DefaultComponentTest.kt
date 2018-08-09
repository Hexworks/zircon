package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.hexworks.zircon.internal.event.InternalEvent
import org.hexworks.zircon.api.Modifiers
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultComponentTest {

    lateinit var target: DefaultComponent
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = object : DefaultComponent(
                size = SIZE,
                position = POSITION,
                componentStyleSet = STYLES,
                wrappers = WRAPPERS,
                tileset = tileset) {
            override fun applyColorTheme(colorTheme: ColorTheme) {
                TODO("not implemented")
            }

            override fun acceptsFocus(): Boolean {
                TODO("not implemented")
            }

            override fun giveFocus(input: Maybe<Input>): Boolean {
                TODO("not implemented")
            }

            override fun takeFocus(input: Maybe<Input>) {
                TODO("not implemented")
            }
        }
    }

    @Test
    fun shouldUseFontFromComponentWhenTransformingToLayer() {
        val result = target.transformToLayers()
        result.forEach {
            assertThat(it.tileset().id).isEqualTo(tileset.id)
        }
    }

    @Test
    fun shouldProperlyApplyStylesOnInit() {
        assertThat(target.getComponentStyles().getCurrentStyle())
                .isEqualTo(STYLES.getCurrentStyle())
    }

    @Test
    fun shouldProperlyApplyStylesOnMouseOver() {

        EventBus.sendTo(target.id, InternalEvent.MouseOver(MouseAction(MouseActionType.MOUSE_ENTERED, 1, Position.defaultPosition())))

        val targetChar = target.getDrawSurface().getTileAt(Position.defaultPosition()).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(MOUSE_OVER_STYLE.backgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(MOUSE_OVER_STYLE.foregroundColor())
    }

    @Test
    fun shouldProperlyApplyStylesOnMouseOut() {
        EventBus.sendTo(target.id, InternalEvent.MouseOver(MouseAction(MouseActionType.MOUSE_ENTERED, 1, Position.defaultPosition())))

        EventBus.sendTo(target.id, InternalEvent.MouseOut(MouseAction(MouseActionType.MOUSE_EXITED, 1, Position.defaultPosition())))

        val targetChar = target.getDrawSurface().getTileAt(Position.defaultPosition()).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(DEFAULT_STYLE.backgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(DEFAULT_STYLE.foregroundColor())
    }

    @Test
    fun shouldProperlySetNewPosition() {
        target.moveTo(NEW_POSITION)

        assertThat(target.position()).isEqualTo(NEW_POSITION)
    }

    @Test
    fun shouldContainBoundableWhichIsContained() {
        assertThat(target.containsBoundable(DefaultBoundable(SIZE - Size.one(), POSITION))).isTrue()
    }

    @Test
    fun shouldNotContainBoundableWhichIsContained() {
        assertThat(target.containsBoundable(DefaultBoundable(SIZE + Size.one(), POSITION))).isFalse()
    }

    @Test
    fun shouldContainPositionWhichIsContained() {
        assertThat(target.containsPosition(POSITION)).isTrue()
    }

    @Test
    fun shouldNotContainPositionWhichIsContained() {
        assertThat(target.containsPosition(POSITION - Position.offset1x1())).isFalse()
    }

    @Test
    fun shouldProperlyDrawOntoTileGraphic() {
        val image = TileGraphicBuilder.newBuilder()
                .size(SIZE + Size.create(POSITION.x, POSITION.y))
                .build()
        target.drawOnto(image)

        assertThat(image.getTileAt(POSITION - Position.offset1x1()).get())
                .isEqualTo(Tile.empty())

        target.size().fetchPositions().forEach {
            assertThat(image.getTileAt(it + POSITION).get())
                    .isEqualTo(target.getDrawSurface().getTileAt(it).get())
        }
    }

    @Test
    fun shouldProperlyFetchByPositionWhenContainsPosition() {
        assertThat(target.fetchComponentByPosition(POSITION).get()).isEqualTo(target)
    }

    @Test
    fun shouldNotFetchByPositionWhenDoesNotContainPosition() {
        assertThat(target.fetchComponentByPosition(Position.create(100, 100)).isPresent).isFalse()
    }

    @Test
    fun shouldProperlyListenToMousePress() {
        val pressed = AtomicBoolean(false)
        target.onMousePressed(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                pressed.set(true)
            }
        })

        EventBus.sendTo(target.id, InternalEvent.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, POSITION)))

        assertThat(pressed.get()).isTrue()
    }

    @Test
    fun shouldNotListenToMousePressOnOtherComponents() {
        val pressed = AtomicBoolean(false)
        target.onMousePressed(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                pressed.set(true)
            }
        })

        EventBus.sendTo(Identifier.randomIdentifier(), InternalEvent.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, POSITION)))

        assertThat(pressed.get()).isFalse()
    }

    @Test
    fun shouldProperlyListenToMouseRelease() {
        val pressed = AtomicBoolean(false)
        target.onMouseReleased(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                pressed.set(true)
            }
        })

        EventBus.sendTo(target.id, InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(pressed.get()).isTrue()
    }

    @Test
    fun shouldNotListenToMouseReleaseOnOtherComponents() {
        val pressed = AtomicBoolean(false)
        target.onMouseReleased(object : Consumer<MouseAction> {
            override fun accept(t: MouseAction) {
                pressed.set(true)
            }
        })

        EventBus.sendTo(Identifier.randomIdentifier(), InternalEvent.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(pressed.get()).isFalse()
    }

    @Test
    fun shouldProperlyTransformToLayers() {
        val result = target.transformToLayers()
        assertThat(result).hasSize(1)
        assertThat(result.first().size()).isEqualTo(target.size())
        assertThat(result.first().position()).isEqualTo(target.position())
    }

    @Test
    fun shouldBeEqualToItself() {
        assertThat(target).isEqualTo(target)
    }

    companion object {
        val FONT = CP437TilesetResource.ROGUE_YUN_16X16
        val SIZE = Size.create(4, 4)
        val POSITION = Position.create(2, 3)
        val NEW_POSITION = Position.create(6, 7)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.BLUE)
                .foregroundColor(ANSITileColor.RED)
                .build()
        val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.GREEN)
                .foregroundColor(ANSITileColor.YELLOW)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.MAGENTA)
                .foregroundColor(ANSITileColor.BLUE)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.YELLOW)
                .foregroundColor(ANSITileColor.CYAN)
                .build()
        val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.RED)
                .foregroundColor(ANSITileColor.CYAN)
                .build()
        val STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .activeStyle(ACTIVE_STYLE)
                .disabledStyle(DISABLED_STYLE)
                .focusedStyle(FOCUSED_STYLE)
                .mouseOverStyle(MOUSE_OVER_STYLE)
                .build()
        val WRAPPERS = listOf(
                ShadowWrappingStrategy(),
                BorderWrappingStrategy(Modifiers.border()))
    }
}
