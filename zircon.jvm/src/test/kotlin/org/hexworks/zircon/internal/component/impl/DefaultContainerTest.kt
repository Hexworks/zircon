package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.hexworks.zircon.internal.event.InternalEvent
import org.hexworks.zircon.api.Modifiers
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultContainerTest {

    lateinit var target: DefaultContainer
    lateinit var goodTileset: TilesetResource
    lateinit var badTileset: TilesetResource

    @Before
    fun setUp() {
        goodTileset = GOOD_FONT
        badTileset = BAD_FONT
        target = DefaultContainer(
                initialSize = SIZE,
                position = POSITION,
                componentStyleSet = STYLES,
                wrappers = WRAPPERS,
                initialTileset = goodTileset)
    }

    @Test
    fun shouldProperlySetPositionsWhenAContainerWithComponentsIsAddedToTheComponentTree() {
        val grid = TileGridBuilder.newBuilder()
                .size(Size.create(40, 25))
                .tileset(BuiltInCP437Tileset.REX_PAINT_16X16)
                .build()

        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .size(Size.create(32, 16))
                .position(Position.offset1x1())
                .build()

        val panel1 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel2")
                .size(Size.create(16, 10))
                .position(Position.offset1x1())
                .build()

        val header0 = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header")
                .build()

        val header1 = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header2")
                .build()


        panel0.addComponent(header0)
        panel1.addComponent(header1)
        panel0.addComponent(panel1)

        screen.addComponent(panel0)

        assertThat(panel0.position()).isEqualTo(Position.create(1, 1))
        assertThat(panel1.position()).isEqualTo(Position.create(3, 3))
        assertThat(header0.position()).isEqualTo(Position.create(3, 2))
        assertThat(header1.position()).isEqualTo(Position.create(5, 4))
    }

    @Test
    fun shouldProperlySetPositionsWhenAContainerIsAddedToTheComponentTreeThenComponentsAreAddedToIt() {
        val grid = TileGridBuilder.newBuilder()
                .size(Size.create(40, 25))
                .tileset(BuiltInCP437Tileset.REX_PAINT_16X16)
                .build()
        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .size(Size.create(32, 16))
                .position(Position.offset1x1())
                .build()

        val panel1 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel2")
                .size(Size.create(16, 10))
                .position(Position.offset1x1())
                .build()

        val header0 = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header")
                .build()

        val header1 = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header2")
                .build()


        panel0.addComponent(panel1)
        panel0.addComponent(header0)
        panel1.addComponent(header1)

        screen.addComponent(panel0)

        assertThat(panel0.position()).isEqualTo(Position.create(1, 1))
        assertThat(panel1.position()).isEqualTo(Position.create(3, 3))
        assertThat(header0.position()).isEqualTo(Position.create(3, 2))
        assertThat(header1.position()).isEqualTo(Position.create(5, 4))
    }

    @Test
    fun shouldProperlySetPositionsWhenAComponentIsAddedToAContainerAfterItIsAttachedToTheScreen() {
        val grid = TileGridBuilder.newBuilder()
                .size(Size.create(40, 25))
                .tileset(BuiltInCP437Tileset.REX_PAINT_16X16)
                .build()
        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .size(Size.create(32, 16))
                .position(Position.offset1x1())
                .build()

        val header0 = HeaderBuilder.newBuilder()
                .position(Position.create(1, 0))
                .text("Header")
                .build()

        screen.addComponent(panel0)

        panel0.addComponent(header0)


        assertThat(panel0.position()).isEqualTo(Position.create(1, 1))
        assertThat(header0.position()).isEqualTo(Position.create(3, 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        target.addComponent(LabelBuilder.newBuilder()
                .text("foo")
                .tileset(badTileset)
                .build())
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIntersectsWithAnother() {
        val pos = Position.create(1, 1)
        val comp = LabelBuilder.newBuilder().position(pos).text("text").build()
        val otherComp = LabelBuilder.newBuilder().position(pos.withRelativeX(1)).text("text").build()
        target.addComponent(comp)
        target.addComponent(otherComp)
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        assertThat(target.giveFocus()).isFalse()
    }

    @Test
    fun shouldProperlyRemoveComponentFromSelf() {
        val comp = LabelBuilder.newBuilder()
                .text("xLength")
                .position(Position.defaultPosition())
                .build()
        target.addComponent(comp)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe<InternalEvent.ComponentRemoval> {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    @Test
    fun shouldProperlyRemoveComponentFromChild() {
        val comp = LabelBuilder.newBuilder()
                .text("xLength")
                .position(Position.defaultPosition())
                .build()
        val panel = PanelBuilder.newBuilder()
                .size(SIZE - Size.one())
                .position(Position.defaultPosition()).build()
        panel.addComponent(comp)
        target.addComponent(panel)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe<InternalEvent.ComponentRemoval> {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    companion object {
        val GOOD_FONT = BuiltInCP437Tileset.AESOMATICA_16X16
        val BAD_FONT = BuiltInCP437Tileset.BISASAM_20X20
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
