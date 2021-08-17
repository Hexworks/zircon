package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName")
class RootContainerTest : ComponentImplementationTest<RootContainer>() {

    override lateinit var target: RootContainer
    override lateinit var graphics: TileGraphics

    private lateinit var applicationStub: ApplicationStub

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                    .withBackgroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                    .build()
            )
            .build()

    @Before
    override fun setUp() {
        applicationStub = ApplicationStub()
        rendererStub = ComponentRendererStub()
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_3_4).build()
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

        val label = Components.label()
            .withPosition(pos)
            .withSize(Size.one())
            .withTileset(TILESET_REX_PAINT_20X20)
            .build()

        target.addComponent(label)

        assertThat(target.fetchComponentByPosition(POSITION_2_3 + pos).get()).isEqualTo(label)
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_out_of_bounds_component_Then_it_is_not_present() {
        assertThat(target.fetchComponentByPosition(Position.create(Int.MAX_VALUE, Int.MAX_VALUE)).isPresent).isFalse()
    }

    @Test
    fun Given_a_root_component_When_trying_to_fetch_child_of_child_Then_it_is_present() {

        val label0Pos = Position.create(1, 1)
        val label1Pos = Position.create(1, 2)
        val panelPos = Position.create(1, 1)

        val panel = Components.panel()
            .withSize(Size.create(2, 3))
            .withPosition(panelPos)
            .withTileset(TILESET_REX_PAINT_20X20)
            .build()

        val label0 = Components.label()
            .withPosition(label0Pos)
            .withSize(Size.one())
            .withTileset(TILESET_REX_PAINT_20X20)
            .build()

        val label1 = Components.label()
            .withPosition(label1Pos)
            .withSize(Size.one())
            .withTileset(TILESET_REX_PAINT_20X20)
            .build()

        panel.addComponents(label0, label1)
        target.addComponent(panel)

        assertThat(target.fetchComponentByPosition(POSITION_2_3 + label0Pos + panelPos).get())
            .isEqualTo(label0)
    }

    @Test
    fun Given_an_empty_root_component_When_trying_to_fetch_self_Then_it_is_present() {

        assertThat(target.fetchComponentByPosition(POSITION_2_3).get().id)
            .isEqualTo(target.id)
    }


    @Test
    fun shouldProperlyCalculatePathFromRoot() {

        val panel = Components.panel()
            .withSize(Size.create(2, 3))
            .withPosition(Position.offset1x1())
            .withTileset(TILESET_REX_PAINT_20X20)
            .build().asInternalComponent()

        val label = Components.label()
            .withPosition(Position.offset1x1())
            .withSize(Size.one())
            .withTileset(TILESET_REX_PAINT_20X20)
            .build().asInternalComponent()

        panel.addComponent(label)
        target.addComponent(panel)

        assertThat(target.calculatePathTo(label)).containsExactly(target, panel, label)
    }

    companion object {

        fun label(tileset: TilesetResource, text: String): InternalComponent = Components.label()
            .withTileset(tileset)
            .withText(text)
            .build().asInternalComponent()

        fun vbox(tileset: TilesetResource): InternalContainer = Components.vbox()
            .withTileset(tileset)
            .withPreferredSize(3, 4)
            .build().asInternalComponent()
    }
}























