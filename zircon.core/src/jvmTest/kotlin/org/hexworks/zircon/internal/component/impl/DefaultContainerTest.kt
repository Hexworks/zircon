package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("UsePropertyAccessSyntax")
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
            metadata = ComponentMetadata(
                size = SIZE_4x4,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderer = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub
            )
        )
    }

    @Test
    fun shouldProperlySetUpComponentsWhenNestedComponentsAreAdded() {
        val grid = TileGridBuilder.newBuilder()
            .withConfig(
                AppConfig.newBuilder()
                    .withSize(40, 25)
                    .withDefaultTileset(TILESET_REX_PAINT_20X20)
                    .build()
            )
            .build() as ThreadSafeTileGrid

        grid.application = ApplicationStub()

        val screen = ScreenBuilder.createScreenFor(grid)

        val panel = PanelBuilder.newBuilder()
            .withDecorations(box(title = "Panel"))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withPreferredSize(32, 16)
            .withAlignment(positionalAlignment(1, 1))
            .build() as InternalContainer
        val panelHeader = HeaderBuilder.newBuilder()
            .withAlignment(positionalAlignment(Position.create(1, 0)))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withText("Header")
            .build() as InternalComponent

        val innerPanelHeader = HeaderBuilder.newBuilder()
            .withAlignment(positionalAlignment(1, 0))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withText("Header2")
            .build() as InternalComponent
        val innerPanel = PanelBuilder.newBuilder()
            .withDecorations(box(title = "Panel2"))
            .withPreferredSize(16, 10)
            .withTileset(TILESET_REX_PAINT_20X20)
            .withAlignment(positionalAlignment(1, 2))
            .build() as InternalContainer

        assertThat(panel.isAttached).isFalse()
        assertThat(panelHeader.isAttached).isFalse()
        assertThat(innerPanel.isAttached).isFalse()
        assertThat(innerPanelHeader.isAttached).isFalse()

        innerPanel.addComponent(innerPanelHeader)
        assertThat(innerPanelHeader.isAttached).isTrue()

        panel.addComponent(panelHeader)
        assertThat(panelHeader.isAttached).isTrue()

        panel.addComponent(innerPanel)
        assertThat(innerPanel.isAttached).isTrue()

        assertThat(panel.isAttached).isFalse()

        screen.addComponent(panel)

        assertThat(panel.isAttached).isTrue()

        assertThat(panel.absolutePosition).isEqualTo(Position.create(1, 1))
        assertThat(panelHeader.absolutePosition).isEqualTo(Position.create(3, 2)) // + 1x1 because of the wrapper
        assertThat(innerPanel.absolutePosition).isEqualTo(Position.create(3, 4))
        assertThat(innerPanelHeader.absolutePosition).isEqualTo(Position.create(5, 5))
    }

    @Test
    fun shouldProperlySetUpComponentsWhenAContainerIsAddedThenComponentsAreAddedToIt() {
        val grid: ThreadSafeTileGrid = TileGridBuilder.newBuilder()
            .withConfig(
                AppConfig.newBuilder()
                    .withSize(40, 25)
                    .withDefaultTileset(TILESET_REX_PAINT_20X20)
                    .build()
            )
            .build() as ThreadSafeTileGrid
        grid.application = ApplicationStub()

        val screen = ScreenBuilder.createScreenFor(grid)

        val panel0 = PanelBuilder.newBuilder()
            .withDecorations(box(title = "Panel"))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withPreferredSize(32, 16)
            .withAlignment(positionalAlignment(Position.offset1x1()))
            .build() as InternalContainer
        val panel1 = PanelBuilder.newBuilder()
            .withDecorations(box(title = "Panel2"))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withPreferredSize(16, 10)
            .withAlignment(positionalAlignment(1, 1))
            .build() as InternalContainer
        val header0 = HeaderBuilder.newBuilder()
            .withAlignment(positionalAlignment(1, 0))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withText("Header")
            .build() as InternalComponent

        screen.addComponent(panel0)

        assertThat(panel0.isAttached).isTrue()

        panel0.addComponent(header0)

        assertThat(header0.isAttached).isTrue()
        assertThat(header0.absolutePosition).isEqualTo(Position.create(3, 2))

        panel0.addComponent(panel1)

        assertThat(panel1.isAttached)
        assertThat(panel1.absolutePosition).isEqualTo(Position.create(3, 3))

    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfComponentWithUnsupportedFontSizeIsAdded() {
        target.addComponent(
            LabelBuilder.newBuilder()
                .withText("foo")
                .withTileset(badTileset)
                .build()
        )
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLetToAddAComponentWhichIntersectsWithAnother() {
        val pos = Position.create(1, 1)
        val comp = LabelBuilder.newBuilder()
            .withAlignment(positionalAlignment(pos))
            .withTileset(TILESET_REX_PAINT_20X20)
            .withText("text")
            .build()
        val otherComp = LabelBuilder.newBuilder()
            .withAlignment(positionalAlignment(pos.withRelativeX(1)))
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
    fun shouldNotAllowAddingAComponentToItself() {
        target.addComponent(target)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToAddAComponentWhichIntersectsWithOtherComponents() {
        target.addComponent(
            LabelBuilder.newBuilder()
                .withAlignment(positionalAlignment(Position.zero()))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("foo")
                .build()
        )

        target.addComponent(
            LabelBuilder.newBuilder()
                .withAlignment(positionalAlignment(Position.create(1, 0)))
                .withTileset(TILESET_REX_PAINT_20X20)
                .withText("foo")
                .build()
        )
    }

    @Test
    fun shouldProperlyRemoveComponentFromSelf() {
        val applicationStub = ApplicationStub()
        val root = DefaultRootContainer(
            metadata = ComponentMetadata(
                relativePosition = Position.defaultPosition(),
                size = Size.create(50, 50),
            ),
            application = applicationStub,
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = RootContainerRenderer()
            )
        )
        val comp = LabelBuilder.newBuilder()
            .withText("x")
            .withTileset(TILESET_REX_PAINT_20X20)
            .withAlignment(positionalAlignment(Position.defaultPosition()))
            .build() as InternalComponent
        root.addComponent(target)

        val handle = target.addComponent(comp)
        val removalHappened = AtomicBoolean(false)
        root.eventBus.simpleSubscribeTo<ComponentRemoved>(root.eventScope) {
            removalHappened.set(true)
        }

        handle.detach()
        assertThat(removalHappened.get()).isTrue()
    }

    @Test
    fun shouldReturnUnknownStylesWhenThemeApplied() {
        assertThat(target.convertColorTheme(DEFAULT_THEME)).isSameAs(ComponentStyleSet.unknown())
    }

    @Test
    fun shouldProperlyReturnToString() {
        println(target.toString())
        assertThat(target.toString())
            .isEqualTo(
                "DefaultContainer(id=${
                    target.id.toString().substring(0, 4)
                }, absolutePosition=(2,3), relativePosition=(2,3), size=(4X4), state=DEFAULT, disabled=false)"
            )
    }

    companion object {
        val SIZE_4x4 = Size.create(4, 4)
        val COMPONENT_STUB_POSITION_1x1 = Position.create(1, 1)
    }
}
