package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Visibility
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.handleMouseEvents
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase.BUBBLE
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.component.InternalContainer
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate")
class DefaultComponentTest : CommonComponentTest<DefaultComponent>() {

    override lateinit var target: DefaultComponent

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSet.empty()

    var rendered = false
    lateinit var appliedColorTheme: ColorTheme

    @Before
    override fun setUp() {
        componentStub = ComponentStub(DefaultContainerTest.COMPONENT_STUB_POSITION_1x1, Size.create(2, 2))
        rendererStub = ComponentRendererStub()
        target = object : DefaultComponent(
                componentMetadata = ComponentMetadata(
                        size = DefaultContainerTest.SIZE_4x4,
                        position = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderer = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub)) {
            override fun render() {
                rendered = true
            }

            override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
                appliedColorTheme = colorTheme
                return ComponentStyleSet.empty()
            }

            override fun acceptsFocus(): Boolean {
                return false
            }

        }
    }

    @Test
    fun shouldUseTilesetFromComponentWhenTransformingToLayer() {
        target.toFlattenedLayers().forEach {
            assertThat(it.currentTileset().id).isEqualTo(TILESET_REX_PAINT_20X20.id)
        }
    }


    @Test
    fun shouldProperlyApplyStylesOnInit() {
        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(ComponentState.DEFAULT)
    }

    @Test
    fun shouldProperlySetNewPosition() {
        target.moveTo(NEW_POSITION_6x7)

        assertThat(target.position).isEqualTo(NEW_POSITION_6x7)
    }

    @Test
    fun shouldContainBoundableWhichIsContained() {
        assertThat(target.containsBoundable(Rect.create(POSITION_2x3, SIZE_4x4 - Size.one()))).isTrue()
    }

    @Test
    fun shouldNotContainBoundableWhichIsContained() {
        assertThat(target.containsBoundable(Rect.create(POSITION_2x3, SIZE_4x4 + Size.one()))).isFalse()
    }

    @Test
    fun shouldContainPositionWhichIsContained() {
        assertThat(target.containsPosition(POSITION_2x3)).isTrue()
    }

    @Test
    fun shouldNotContainPositionWhichIsContained() {
        assertThat(target.containsPosition(POSITION_2x3 - Position.offset1x1())).isFalse()
    }

    @Test
    fun shouldProperlyDrawOntoTileGraphic() {
        val image = TileGraphicsBuilder.newBuilder()
                .withSize(SIZE_4x4 + Size.create(POSITION_2x3.x, POSITION_2x3.y))
                .build()
        val filler = Tile.defaultTile().withCharacter('f')
        target.fill(filler)
        target.drawOnto(image, POSITION_2x3)

        assertThat(image.getTileAt(POSITION_2x3 - Position.offset1x1()).get())
                .isEqualTo(Tile.empty())

        target.size.fetchPositions().forEach {
            assertThat(image.getTileAt(it + POSITION_2x3).get())
                    .isEqualTo(filler)
        }
    }

    @Test
    fun shouldRenderWhenComponentStyleSetIsSet() {
        target.componentStyleSet = ComponentStyleSet.empty()

        assertThat(rendered).isTrue()
    }

    @Test
    fun shouldRenderWhenVisibilityIsSet() {
        target.isVisible = Visibility.Hidden

        assertThat(rendered).isTrue()
    }

    @Test
    fun shouldNotifyObserversWhenInputIsEmitted() {
        var notified = false

        target.handleMouseEvents(MOUSE_CLICKED) { _, _ ->
            notified = true
            Processed
        }

        target.process(
                event = MouseEvent(MOUSE_CLICKED, 1, Position.defaultPosition()),
                phase = BUBBLE)

        assertThat(notified).isTrue()
    }

    @Test
    fun shouldProperlyCreateSnapshot() {
        target.fill(TileBuilder.newBuilder().withCharacter('x').build())
        val result = target.createSnapshot()
        val cells = result.cells.toList()

        assertThat(result.tileset).isEqualTo(target.currentTileset())
        assertThat(cells.size).isEqualTo(16)
        assertThat(cells.first().position).isEqualTo(POSITION_2x3)
    }

    @Test
    fun shouldProperlyHandleMouseEntered() {
        target.mouseEntered(
                event = MouseEvent(MOUSE_ENTERED, 1, Position.defaultPosition()),
                phase = TARGET)

        assertThat(target.componentStyleSet.currentState()).isEqualTo(ComponentState.MOUSE_OVER)
        assertThat(rendered).isTrue()
    }

    @Test
    fun shouldProperlyCalculateAbsolutePositionWithDeeplyNestedComponents() {

        val rootPos = Position.create(1, 1)
        val parentPos = Position.create(2, 1)
        val leafPos = Position.create(1, 2)

        val root = PanelBuilder.newBuilder()
                .withSize(Size.create(10, 10))
                .withPosition(rootPos)
                .build()

        val parent = PanelBuilder.newBuilder()
                .withSize(Size.create(7, 7))
                .withPosition(parentPos)
                .build()

        root.addComponent(parent)

        val leaf = LabelBuilder.newBuilder()
                .withPosition(leafPos)
                .withText("foo")
                .build()

        parent.addComponent(leaf)

        assertThat(leaf.absolutePosition).isEqualTo(rootPos + parentPos + leafPos)
    }

    @Test
    fun shouldProperlyFetchByPositionWhenContainsPosition() {
        assertThat(target.fetchComponentByPosition(POSITION_2x3).get()).isEqualTo(target)
    }

    @Test
    fun shouldNotFetchByPositionWhenDoesNotContainPosition() {
        assertThat(target.fetchComponentByPosition(Position.create(100, 100)).isPresent).isFalse()
    }

    @Test
    fun shouldProperlyListenToMousePress() {
        val pressed = AtomicBoolean(false)
        target.handleMouseEvents(MOUSE_PRESSED) { _, _ ->
            pressed.set(true)
            Processed
        }

        target.process(
                event = MouseEvent(MOUSE_PRESSED, 1, POSITION_2x3),
                phase = BUBBLE)

        assertThat(pressed.get()).isTrue()
    }

    @Test
    fun shouldNotListenToMousePressOnOtherComponents() {
        // TODO: move this test to component container!
    }

    @Test
    fun shouldProperlyListenToMouseRelease() {
        // TODO: move this test to component container!
    }

    @Test
    fun shouldNotListenToMouseReleaseOnOtherComponents() {
        // TODO: move this test to component container!
    }

    @Test
    fun shouldProperlyTransformToLayers() {
        val result = target.toFlattenedLayers()
        assertThat(result).hasSize(1)
        assertThat(result.first().size).isEqualTo(target.size)
        assertThat(result.first().position).isEqualTo(target.position)
    }

    @Test
    fun shouldBeEqualToItself() {
        assertThat(target).isEqualTo(target)
    }

    @Test
    fun shouldProperlyCalculatePathFromRoot() {
        val root = RootContainer(
                componentMetadata = ComponentMetadata(
                        position = Positions.defaultPosition(),
                        size = Sizes.create(100, 100),
                        tileset = TILESET_REX_PAINT_20X20,
                        componentStyleSet = ComponentStyleSet.empty()),
                renderingStrategy = DefaultComponentRenderingStrategy(NoOpGenericRenderer()))

        val parent = Components.panel()
                .withSize(50, 50)
                .withTileset(TILESET_REX_PAINT_20X20)
                .build() as InternalContainer

        root.addComponent(parent)
        parent.addComponent(target)

        assertThat(target.calculatePathFromRoot()).containsExactly(root, parent, target)
    }

    companion object {
        val POSITION_2x3 = Position.create(2, 3)
        val NEW_POSITION_6x7 = Position.create(6, 7)
        val SIZE_4x4 = Size.create(4, 4)
    }
}
