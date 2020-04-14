package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.ComponentRendererStub
import org.hexworks.zircon.internal.component.impl.DefaultRootContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test


@Suppress("TestFunctionName")
class DefaultComponentFocusOrderListTest {

    lateinit var rendererStub: ComponentRendererStub<RootContainer>
    lateinit var root: RootContainer
    lateinit var target: DefaultComponentFocusOrderList

    @Before
    fun setUp() {
        rendererStub = ComponentRendererStub()
        root = DefaultRootContainer(
                componentMetadata = ComponentMetadata(
                        relativePosition = Position.zero(),
                        size = Size.create(100, 100),
                        tileset = CP437TilesetResources.aduDhabi16x16(),
                        componentStyleSet = ComponentStyleSet.defaultStyleSet()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = rendererStub))
        target = DefaultComponentFocusOrderList(root)
    }

    @Test
    fun When_the_root_has_no_children_Then_next_is_the_root() {
        assertThat(target.findNext()).isEqualTo(root)
    }

    @Test
    fun When_the_root_has_no_children_Then_previous_is_the_root() {
        assertThat(target.findPrevious()).isEqualTo(root)
    }

    @Test
    fun When_the_root_has_children_Then_focus_order_is_the_flattened_tree() {

        val button0 = Components.button().build() as InternalComponent
        val box = Components.vbox().withSize(10, 2).withPosition(Position.bottomLeftOf(button0)).build()
        val button1a = Components.button().build() as InternalComponent
        val button1b = Components.button().build() as InternalComponent

        box.addComponents(button1a, button1b)
        root.addComponents(button0, box)

        target.refreshFocusables()

        assertThat(target.findNext()).isEqualTo(button0)
        target.focus(root)
        assertThat(target.findNext()).isEqualTo(button0)
        target.focus(button0)
        assertThat(target.findNext()).isEqualTo(button1a)
        target.focus(button1a)
        assertThat(target.findNext()).isEqualTo(button1b)
    }

    @Test
    fun When_the_last_component_is_focused_Then_the_next_focus_is_the_root() {

        val button0 = Components.button().build() as InternalComponent
        val button1 = Components.button()
                .withPosition(Position.bottomLeftOf(button0))
                .build() as InternalComponent

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button1)

        assertThat(target.findNext()).isEqualTo(root)
    }

    @Test
    fun When_the_first_component_is_focused_Then_the_previous_focus_is_the_root() {

        val button0 = Components.button().build() as InternalComponent
        val button1 = Components.button()
                .withPosition(Position.bottomLeftOf(button0))
                .build() as InternalComponent

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button0)

        assertThat(target.findPrevious()).isEqualTo(root)
    }

    @Test
    fun When_the_root_is_focused_Then_the_next_focus_is_the_first_component() {

        val button0 = Components.button().build() as InternalComponent
        val button1 = Components.button()
                .withPosition(Position.bottomLeftOf(button0))
                .build() as InternalComponent

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        assertThat(target.findNext()).isEqualTo(button0)
    }

    @Test
    fun When_the_root_is_focused_Then_the_previous_focus_is_the_last_component() {

        val button0 = Components.button().build() as InternalComponent
        val button1 = Components.button()
                .withPosition(Position.bottomLeftOf(button0))
                .build() as InternalComponent

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        assertThat(target.findPrevious()).isEqualTo(button1)
    }

    @Test
    fun When_the_root_has_only_one_child_Then_focus_order_is_correct() {

        val button0 = Components.button().build() as InternalComponent

        root.addComponent(button0)
        target.refreshFocusables()

        assertThat(target.findNext()).isEqualTo(button0)
        target.focus(button0)
        assertThat(target.findNext()).isEqualTo(root)
    }
}
