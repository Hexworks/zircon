package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.extensions.addComponents
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.OFFSET_1X1
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("TestFunctionName")
class RootContainerTest : ComponentImplementationTest<RootContainer>() {

    override lateinit var target: RootContainer
    override lateinit var drawWindow: DrawWindow


    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = DEFAULT_THEME.secondaryBackgroundColor
            }
        }

    @BeforeTest
    override fun setUp() {
        rendererStub = ComponentRendererStub()
        drawWindow = tileGraphics {
            size = SIZE_3_4
        }.toDrawWindow(Boundable.create(size = SIZE_3_4))
        componentStub = ComponentStub(Position(1, 1), Size(2, 2))
        target = DefaultRootContainer(
            metadata = ComponentMetadata(
                position = POSITION_2_3,
                size = SIZE_3_4,
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty(),
                componentStyleSetProperty = COMPONENT_STYLES.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub
            ),
            eventBus = EventBus.create(),
            eventScope = ZirconScope(),
        )
    }

    @Test
    fun The_root_container_is_attached() {
        target.isAttached shouldBe true
    }

    @Test
    fun Given_a_root_container_When_adding_multiple_components_to_it_Then_the_layer_states_are_correct() {

        val box = vBox(target.tileset)
        val foo = label(target.tileset, "foo")
        val bar = label(target.tileset, "bar")
        box.addComponents(foo, bar)
        target.addComponent(box)

        target.componentTree.map { it.id } shouldBe
                listOf(target.id, box.id, foo.id, bar.id)
    }

    @Test
    fun Given_a_root_container_When_adding_components_to_its_child_Then_the_layer_states_are_correct() {

        val box = vBox(target.tileset)
        target.addComponent(box)

        val foo = label(target.tileset, "foo")
        val bar = label(target.tileset, "bar")
        box.addComponents(foo, bar)

        target.componentTree.map { it.id } shouldBe
                listOf(target.id, box.id, foo.id, bar.id)
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_child_Then_it_is_present() {

        val pos = Position(1, 2)

        val label = label {
            position = pos
            preferredSize = Size.ONE
            tileset = TILESET_REX_PAINT_20X20
        }

        target.addComponent(label)

        target.fetchComponentByPositionOrNull(POSITION_2_3 + pos) shouldBe label
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_out_of_bounds_component_Then_it_is_not_present() {
        target.fetchComponentByPositionOrNull(Position(Int.MAX_VALUE, Int.MAX_VALUE)) shouldBe null
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_child_of_child_Then_it_is_present() {

        val label0Pos = Position(1, 1)
        val label1Pos = Position(1, 2)
        val panelPos = Position(1, 1)

        val panel = panel {
            preferredSize = Size(2, 3)
            position = panelPos
            tileset = TILESET_REX_PAINT_20X20
        }

        val label0 = label {
            position = label0Pos
            preferredSize = Size.ONE
            tileset = TILESET_REX_PAINT_20X20
        }

        val label1 = label {
            position = label1Pos
            preferredSize = Size.ONE
            tileset = TILESET_REX_PAINT_20X20
        }

        panel.addComponents(label0, label1)
        target.addComponent(panel)

        target.fetchComponentByPositionOrNull(POSITION_2_3 + label0Pos + panelPos) shouldBe label0
    }

    @Test
    fun Given_an_empty_root_component_When_trying_to_fetch_self_Then_it_is_present() {

        target.fetchComponentByPositionOrNull(POSITION_2_3)?.id shouldBe target.id
    }


    @Test
    fun shouldProperlyCalculatePathFromRoot() {

        val panel = panel {
            preferredSize = Size(2, 3)
            position = OFFSET_1X1
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        val label = label {
            position = OFFSET_1X1
            preferredSize = Size.ONE
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        panel.addComponent(label)
        target.addComponent(panel)

        target.calculatePathTo(label) shouldContainExactly listOf(target, panel, label)
    }

    companion object {

        fun label(tileset: TilesetResource, text: String): InternalComponent = label {
            +text
            this.tileset = tileset
        }.asInternalComponent()

        fun vBox(tileset: TilesetResource): InternalContainer = vBox {
            this.tileset = tileset
            preferredSize = Size(3, 4)
        }.asInternalComponent()
    }
}
