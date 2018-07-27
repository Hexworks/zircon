package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.input.MouseActionType
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.codetome.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultComponentTest {

    lateinit var target: DefaultComponent
    lateinit var tileset: Tileset

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        tileset = FONT.toFont()
        target = object : DefaultComponent(
                initialSize = SIZE,
                position = POSITION,
                componentStyleSet = STYLES,
                wrappers = WRAPPERS,
                initialTileset = tileset) {
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
            assertThat(it.getCurrentFont().getId()).isEqualTo(tileset.getId())
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
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        EventBus.sendTo(target.getId(), Event.MouseOver(MouseAction(MouseActionType.MOUSE_ENTERED, 1, Position.defaultPosition())))

        val targetChar = target.getDrawSurface().getTileAt(Position.defaultPosition()).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(MOUSE_OVER_STYLE.getBackgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(MOUSE_OVER_STYLE.getForegroundColor())
        assertThat(componentChanged.get()).isTrue()
    }

    @Test
    fun shouldProperlyApplyStylesOnMouseOut() {
        EventBus.sendTo(target.getId(), Event.MouseOver(MouseAction(MouseActionType.MOUSE_ENTERED, 1, Position.defaultPosition())))
        val componentChanged = AtomicBoolean(false)
        EventBus.subscribe<Event.ComponentChange> {
            componentChanged.set(true)
        }

        EventBus.sendTo(target.getId(), Event.MouseOut(MouseAction(MouseActionType.MOUSE_EXITED, 1, Position.defaultPosition())))

        val targetChar = target.getDrawSurface().getTileAt(Position.defaultPosition()).get()
        assertThat(targetChar.getBackgroundColor()).isEqualTo(DEFAULT_STYLE.getBackgroundColor())
        assertThat(targetChar.getForegroundColor()).isEqualTo(DEFAULT_STYLE.getForegroundColor())
        assertThat(componentChanged.get()).isTrue()
    }

    @Test
    fun shouldProperlySetNewPosition() {
        target.setPosition(NEW_POSITION)

        assertThat(target.getPosition()).isEqualTo(NEW_POSITION)
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
    fun shouldProperlyDrawOntoTextImage() {
        val image = TileImageBuilder.newBuilder()
                .size(SIZE + Size.create(POSITION.x, POSITION.y))
                .build()
        target.drawOnto(image)

        assertThat(image.getTileAt(POSITION - Position.offset1x1()).get())
                .isEqualTo(Tile.empty())

        target.getBoundableSize().fetchPositions().forEach {
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

        EventBus.sendTo(target.getId(), Event.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, POSITION)))

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

        EventBus.sendTo(Identifier.randomIdentifier(), Event.MousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, POSITION)))

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

        EventBus.sendTo(target.getId(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

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

        EventBus.sendTo(Identifier.randomIdentifier(), Event.MouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, POSITION)))

        assertThat(pressed.get()).isFalse()
    }

    @Test
    fun shouldProperlyTransformToLayers() {
        val result = target.transformToLayers()
        assertThat(result).hasSize(1)
        assertThat(result.first().getBoundableSize()).isEqualTo(target.getBoundableSize())
        assertThat(result.first().getPosition()).isEqualTo(target.getPosition())
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
