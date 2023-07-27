package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.component.buildVbox
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.offset1x1
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size.Companion.one
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName")
class RootContainerTest : ComponentImplementationTest<RootContainer>() {

    override lateinit var target: RootContainer
    override lateinit var drawWindow: DrawWindow

    private lateinit var applicationStub: ApplicationStub

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = DEFAULT_THEME.secondaryBackgroundColor
            }
        }

    @Before
    override fun setUp() {
        applicationStub = ApplicationStub()
        rendererStub = ComponentRendererStub()
        drawWindow = tileGraphics {
            size = SIZE_3_4
        }.toDrawWindow(Rect.create(size = SIZE_3_4))
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
        target = DefaultRootContainer(
            metadata = ComponentMetadata(
                relativePosition = POSITION_2_3,
                size = SIZE_3_4,
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty(),
                componentStyleSetProperty = COMPONENT_STYLES.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub
            ),
            application = applicationStub
        )
    }

    @Test
    fun The_root_container_is_attached() {
        assertThat(target.isAttached).isTrue()
    }

    @Test
    fun Given_a_root_container_When_adding_multiple_components_to_it_Then_the_layer_states_are_correct() {

        val box = vbox(target.tileset)
        val foo = label(target.tileset, "foo")
        val bar = label(target.tileset, "bar")
        box.addComponents(foo, bar)
        target.addComponent(box)

        assertThat(target.componentTree.map { it.id }).isEqualTo(
            listOf(target.id, box.id, foo.id, bar.id)
        )
    }

    @Test
    fun Given_a_root_container_When_adding_components_to_its_child_Then_the_layer_states_are_correct() {

        val box = vbox(target.tileset)
        target.addComponent(box)

        val foo = label(target.tileset, "foo")
        val bar = label(target.tileset, "bar")
        box.addComponents(foo, bar)

        assertThat(target.componentTree.map { it.id }).isEqualTo(
            listOf(target.id, box.id, foo.id, bar.id)
        )
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_child_Then_it_is_present() {

        val pos = Position.create(1, 2)

        val label = buildLabel {
            position = pos
            preferredSize = one()
            tileset = TILESET_REX_PAINT_20X20
        }

        target.addComponent(label)

        assertThat(target.fetchComponentByPositionOrNull(POSITION_2_3 + pos)).isEqualTo(label)
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_out_of_bounds_component_Then_it_is_not_present() {
        assertThat(target.fetchComponentByPositionOrNull(Position.create(Int.MAX_VALUE, Int.MAX_VALUE))).isNull()
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_child_of_child_Then_it_is_present() {

        val label0Pos = Position.create(1, 1)
        val label1Pos = Position.create(1, 2)
        val panelPos = Position.create(1, 1)

        val panel = buildPanel {
            withPreferredSize {
                width = 2
                height = 3
            }
            position = panelPos
            tileset = TILESET_REX_PAINT_20X20
        }

        val label0 = buildLabel {
            position = label0Pos
            preferredSize = one()
            tileset = TILESET_REX_PAINT_20X20
        }

        val label1 = buildLabel {
            position = label1Pos
            preferredSize = one()
            tileset = TILESET_REX_PAINT_20X20
        }

        panel.addComponents(label0, label1)
        target.addComponent(panel)

        assertThat(target.fetchComponentByPositionOrNull(POSITION_2_3 + label0Pos + panelPos))
            .isEqualTo(label0)
    }

    @Test
    fun Given_an_empty_root_component_When_trying_to_fetch_self_Then_it_is_present() {

        assertThat(target.fetchComponentByPositionOrNull(POSITION_2_3)?.id)
            .isEqualTo(target.id)
    }


    @Test
    fun shouldProperlyCalculatePathFromRoot() {

        val panel = buildPanel {
            withPreferredSize {
                width = 2
                height = 3
            }
            position = offset1x1()
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        val label = buildLabel {
            position = offset1x1()
            preferredSize = one()
            tileset = TILESET_REX_PAINT_20X20
        }.asInternalComponent()

        panel.addComponent(label)
        target.addComponent(panel)

        assertThat(target.calculatePathTo(label)).containsExactly(target, panel, label)
    }

    companion object {

        fun label(tileset: TilesetResource, text: String): InternalComponent = buildLabel {
            +text
            this.tileset = tileset
        }.asInternalComponent()

        fun vbox(tileset: TilesetResource): InternalContainer = buildVbox {
            this.tileset = tileset
            withPreferredSize {
                width = 3
                height = 4
            }
        }.asInternalComponent()
    }
}























