package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class DefaultContainerTest : CommonComponentTest<DefaultContainer>() {

    override lateinit var target: DefaultContainer

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSet.empty()

    private val badTileset = BuiltInCP437TilesetResource.WANDERLUST_16X16

    @Before
    override fun setUp() {
        componentStub = ComponentStub(COMPONENT_STUB_POSITION_1x1, Size.create(2, 2))
        rendererStub = ComponentRendererStub()
        target = DefaultContainer(
                componentMetadata = ComponentMetadata(
                        size = SIZE_4x4,
                        position = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderer = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub))
    }

    @Test
    fun shouldProperlyDrawOntoDrawSurface() {

        val componentTile = TileBuilder.newBuilder()
                .withCharacter('x')
                .build()
        val surfaceTile = TileBuilder.newBuilder()
                .withCharacter('y')
                .build()

        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(SIZE_3_4 + Size.one())
                .withTileset(TILESET_REX_PAINT_20X20)
                .build()
                .fill(surfaceTile)
        target.fill(componentTile)

        target.drawOnto(surface, Position.offset1x1())

        assertThat(surface.getTileAt(Position.offset1x1()).get()).isEqualTo(componentTile)
        assertThat(surface.getTileAt(Position.zero()).get()).isEqualTo(surfaceTile)
    }

    @Test
    fun shouldReturnEmptyMaybeWhenTryingToFetchComponentWithOutOfBoundsPosition() {
        assertThat(target.fetchComponentByPosition(Position.create(Int.MAX_VALUE, Int.MAX_VALUE)).isPresent).isFalse()
    }

    @Test
    fun shouldProperlyFetchComponentByPositionWhenChildIsFetched() {
        target.addComponent(componentStub)

        assertThat(target
                .fetchComponentByPosition(COMPONENT_STUB_POSITION_1x1 + POSITION_2_3).get().id)
                .isEqualTo(componentStub.id)
    }

    @Test
    fun shouldProperlyFetchComponentByPositionWhenSelfShouldBeReturned() {
        target.addComponent(componentStub)

        assertThat(target.fetchComponentByPosition(Position.zero() + POSITION_2_3).get().id)
                .isEqualTo(target.id)
    }

    @Test
    fun shouldProperlySetUpComponentsWhenNestedComponentsAreAdded() {
        val grid = TileGridBuilder.newBuilder()
                .withSize(Size.create(40, 25))
                .withTileset(TILESET_REX_PAINT_20X20)
                .build()

        val screen = ScreenBuilder.createScreenFor(grid)

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withSize(Size.create(32, 16))
                .withPosition(Position.create(1, 1))
                .build()
        val panelHeader = HeaderBuilder.newBuilder()
                .withPosition(Positions.create(1, 0))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("Header")
                .build()

        val innerPanelHeader = HeaderBuilder.newBuilder()
                .withPosition(Position.create(1, 0))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("Header2")
                .build()
        val innerPanel = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel2")
                .withSize(Size.create(16, 10))
                .withTileset(TILESET_REX_PAINT_20X20)
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

        assertThat(panel.absolutePosition).isEqualTo(Positions.create(1, 1))
        assertThat(panelHeader.absolutePosition).isEqualTo(Positions.create(3, 2)) // + 1x1 because of the wrapper
        assertThat(innerPanel.absolutePosition).isEqualTo(Positions.create(3, 4))
        assertThat(innerPanelHeader.absolutePosition).isEqualTo(Positions.create(5, 5))
    }

    @Test
    fun shouldProperlySetUpComponentsWhenAContainerIsAddedThenComponentsAreAddedToIt() {
        val grid = TileGridBuilder.newBuilder()
                .withSize(Size.create(40, 25))
                .withTileset(TILESET_REX_PAINT_20X20)
                .build()
        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withSize(Size.create(32, 16))
                .withPosition(Position.offset1x1())
                .build()
        val panel1 = PanelBuilder.newBuilder()
                .wrapWithBox(true)
                .withTitle("Panel2")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withSize(Size.create(16, 10))
                .withPosition(Positions.create(1, 1))
                .build()
        val header0 = HeaderBuilder.newBuilder()
                .withPosition(Position.create(1, 0))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("Header")
                .build()

        screen.addComponent(panel0)

        assertThat(panel0.isAttached()).isTrue()

        panel0.addComponent(header0)

        assertThat(header0.isAttached()).isTrue()
        assertThat(header0.absolutePosition).isEqualTo(Positions.create(3, 2))

        panel0.addComponent(panel1)

        assertThat(panel1.isAttached())
        assertThat(panel1.absolutePosition).isEqualTo(Positions.create(3, 3))

    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        AppConfigs.newConfig().disableBetaFeatures().build()
        target.addComponent(LabelBuilder.newBuilder()
                .withText("foo")
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
        val comp = LabelBuilder.newBuilder()
                .withPosition(pos)
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("text")
                .build()
        val otherComp = LabelBuilder.newBuilder()
                .withPosition(pos.withRelativeX(1))
                .withText("text")
                .withTileset(TILESET_REX_PAINT_20X20)
                .build()
        target.addComponent(comp)
        target.addComponent(otherComp)
    }

    @Test
    fun shouldNotAcceptGivenFocus() {
        assertThat(target.focusGiven()).isEqualTo(Pass)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotAllowDrawingAComponentWithWrongPosition() {
        target.draw(componentStub, Position.zero())
    }

    @Test
    fun shouldAddComponentWhenTryingToDrawIt() {
        target.draw(componentStub, componentStub.position)

        assertThat(target.children.asSequence().map { it.id }.contains(componentStub.id))
    }

    @Test
    fun drawingANonComponentShouldDrawItOntoItsDrawSurface() {
        val drawable = Tile.defaultTile().withCharacter('x')

        target.draw(drawable, Position.zero())

        assertThat(target.getTileAt(Position.zero()).get()).isEqualTo(drawable)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotAllowAddingAComponentToItself() {
        target.addComponent(target)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToAddAComponentWhichIntersectsWithOtherComponents() {
        target.addComponent(LabelBuilder.newBuilder()
                .withPosition(Position.zero())
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("foo")
                .build())

        target.addComponent(LabelBuilder.newBuilder()
                .withPosition(Position.create(1, 0))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("foo")
                .build())
    }

    @Test
    fun shouldProperlyRemoveComponentFromSelf() {
        val comp = LabelBuilder.newBuilder()
                .withText("x")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withPosition(Position.defaultPosition())
                .build()
        target.addComponent(comp)
        val removalHappened = AtomicBoolean(false)
        Zircon.eventBus.subscribe<ZirconEvent.ComponentRemoval>(ZirconScope) {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    @Test
    fun shouldProperlyRemoveAllComponentsFromSelf() {
        val comp1 = LabelBuilder.newBuilder()
                .withText("x")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withPosition(Position.defaultPosition())
                .build()
        target.addComponent(comp1)
        val comp2 = LabelBuilder.newBuilder()
                .withText("x")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withPosition(Position.create(1, 2))
                .build()
        target.addComponent(comp2)
        val removalHappened = AtomicBoolean(false)
        Zircon.eventBus.subscribe<ZirconEvent.ComponentRemoval>(ZirconScope) {
            removalHappened.set(true)
        }

        assertThat(target.detachAllComponents()).isTrue()
        assertThat(removalHappened.get()).isTrue()
        assertThat(target.children).isEmpty()
    }

    @Test
    fun shouldReturnEmptyStylesWhenThemeApplied() {
        assertThat(target.applyColorTheme(DEFAULT_THEME)).isEqualTo(ComponentStyleSet.empty())
    }

    @Test
    fun shouldProperlyReturnToString() {
        assertThat(target.toString()).isEqualTo("DefaultContainer(id=${target.id.toString().substring(0, 4)},position=GridPosition(x=2, y=3),size=DefaultSize(width=4, height=4))")
    }

    @Test
    fun shouldProperlyRemoveComponentFromChild() {
        val comp = LabelBuilder.newBuilder()
                .withText("x")
                .withTileset(TILESET_REX_PAINT_20X20)
                .withPosition(Position.defaultPosition())
                .build()
        val panel = PanelBuilder.newBuilder()
                .withSize(SIZE_4x4 - Size.one())
                .withTileset(TILESET_REX_PAINT_20X20)
                .withPosition(Position.defaultPosition()).build()
        panel.addComponent(comp)
        target.addComponent(panel)
        val removalHappened = AtomicBoolean(false)
        Zircon.eventBus.subscribe<ZirconEvent.ComponentRemoval>(ZirconScope) {
            removalHappened.set(true)
        }

        assertThat(target.removeComponent(comp)).isTrue()
        assertThat(removalHappened.get()).isTrue()
    }

    companion object {
        val SIZE_4x4 = Size.create(4, 4)
        val COMPONENT_STUB_POSITION_1x1 = Position.create(1, 1)
    }
}
