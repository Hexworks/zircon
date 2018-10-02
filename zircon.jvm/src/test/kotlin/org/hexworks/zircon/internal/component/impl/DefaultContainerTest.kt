package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultContainerTest {

    lateinit var target: DefaultContainer
    lateinit var goodTileset: TilesetResource
    lateinit var badTileset: TilesetResource

    @Before
    fun setUp() {
        goodTileset = GOOD_TILESET
        badTileset = BAD_TILESET
        target = object : DefaultContainer(
                size = SIZE,
                position = POSITION,
                componentStyles = STYLES,
                tileset = goodTileset,
                renderer = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = DefaultRadioButtonGroupRenderer())) {
            override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun render() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    @Test
    fun shouldProperlySetUpComponentsWhenNestedComponentsAreAdded() {
        val grid = TileGridBuilder.newBuilder()
                .size(Size.create(40, 25))
                .tileset(BuiltInCP437TilesetResource.REX_PAINT_16X16)
                .build()

        val screen = ScreenBuilder.createScreenFor(grid)

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel")
                .withSize(Size.create(32, 16))
                .withPosition(Position.create(1, 1))
                .build()
        val panelHeader = HeaderBuilder.newBuilder()
                .withPosition(Positions.create(1, 0))
                .text("Header")
                .build()

        val innerPanelHeader = HeaderBuilder.newBuilder()
                .withPosition(Position.create(1, 0))
                .text("Header2")
                .build()
        val innerPanel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel2")
                .withSize(Size.create(16, 10))
                .withPosition(Positions.create(1, 2))
                .build()

        assertThat(panel.isAttached()).isFalse()
        assertThat(panelHeader.isAttached()).isFalse()
        assertThat(innerPanel.isAttached()).isFalse()
        assertThat(innerPanelHeader.isAttached()).isFalse()

        innerPanel.addComponent(innerPanelHeader)
        assertThat(innerPanelHeader.isAttached()).isTrue()

        panel.addComponent(panelHeader)
        assertThat(panelHeader.isAttached()).isTrue()

        panel.addComponent(innerPanel)
        assertThat(innerPanel.isAttached()).isTrue()

        assertThat(panel.isAttached()).isFalse()

        screen.addComponent(panel)

        assertThat(panel.isAttached()).isTrue()

        assertThat(panel.absolutePosition()).isEqualTo(Positions.create(1, 1))
        assertThat(panelHeader.absolutePosition()).isEqualTo(Positions.create(3, 2)) // + 1x1 because of the wrapper
        assertThat(innerPanel.absolutePosition()).isEqualTo(Positions.create(3, 4))
        assertThat(innerPanelHeader.absolutePosition()).isEqualTo(Positions.create(5, 5))
    }

    @Test
    fun shouldProperlySetUpComponentsWhenAContainerIsAddedThenComponentsAreAddedToIt() {
        val grid = TileGridBuilder.newBuilder()
                .size(Size.create(40, 25))
                .tileset(BuiltInCP437TilesetResource.REX_PAINT_16X16)
                .build()
        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel")
                .withSize(Size.create(32, 16))
                .withPosition(Position.offset1x1())
                .build()
        val panel1 = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel2")
                .withSize(Size.create(16, 10))
                .withPosition(Positions.create(1, 1))
                .build()
        val header0 = HeaderBuilder.newBuilder()
                .withPosition(Position.create(1, 0))
                .text("Header")
                .build()

        screen.addComponent(panel0)

        assertThat(panel0.isAttached()).isTrue()

        panel0.addComponent(header0)

        assertThat(header0.isAttached()).isTrue()
        assertThat(header0.absolutePosition()).isEqualTo(Positions.create(3, 2))

        panel0.addComponent(panel1)

        assertThat(panel1.isAttached())
        assertThat(panel1.absolutePosition()).isEqualTo(Positions.create(3, 3))

    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        AppConfigs.newConfig().disableBetaFeatures().build()
        target.addComponent(LabelBuilder.newBuilder()
                .text("foo")
                .withTileset(badTileset)
                .build())
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIntersectsWithAnother() {
        AppConfigs.newConfig().disableBetaFeatures().build()
        val pos = Position.create(1, 1)
        val comp = LabelBuilder.newBuilder().withPosition(pos).text("text").build()
        val otherComp = LabelBuilder.newBuilder().withPosition(pos.withRelativeX(1)).text("text").build()
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
                .text("x")
                .withPosition(Position.defaultPosition())
                .build()
        target.addComponent(comp)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe<ZirconEvent.ComponentRemoval> {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    @Test
    fun shouldProperlyRemoveComponentFromChild() {
        val comp = LabelBuilder.newBuilder()
                .text("x")
                .withPosition(Position.defaultPosition())
                .build()
        val panel = PanelBuilder.newBuilder()
                .withSize(SIZE - Size.one())
                .withPosition(Position.defaultPosition()).build()
        panel.addComponent(comp)
        target.addComponent(panel)
        val removalHappened = AtomicBoolean(false)
        EventBus.subscribe<ZirconEvent.ComponentRemoval> {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    companion object {
        val GOOD_TILESET = BuiltInCP437TilesetResource.AESOMATICA_16X16
        val BAD_TILESET = BuiltInCP437TilesetResource.BISASAM_20X20
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
    }
}
