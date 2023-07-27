package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.ApplicationStub
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.buildButton
import org.hexworks.zircon.api.builder.component.buildVbox
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.bottomLeftOf
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.ComponentRendererStub
import org.hexworks.zircon.internal.component.impl.DefaultRootContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test


@Suppress("TestFunctionName")
class DefaultComponentFocusOrderListTest {

    lateinit var rendererStub: ComponentRendererStub<RootContainer>
    lateinit var applicationStub: ApplicationStub
    lateinit var root: RootContainer
    lateinit var target: DefaultComponentFocusOrderList

    @Before
    fun setUp() {
        applicationStub = ApplicationStub()
        rendererStub = ComponentRendererStub()
        root = DefaultRootContainer(
            metadata = ComponentMetadata(
                relativePosition = Position.zero(),
                size = Size.create(100, 100),
                tilesetProperty = CP437TilesetResources.aduDhabi16x16().toProperty(),
                componentStyleSetProperty = ComponentStyleSet.defaultStyleSet().toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub
            ),
            application = applicationStub
        )
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

        val button0 = buildButton { }.asInternalComponent()
        val box = buildVbox {
            withPreferredSize {
                width = 10
                height = 2
            }
            position = bottomLeftOf(button0)
        }
        val button1a = buildButton { }.asInternalComponent()
        val button1b = buildButton { }.asInternalComponent()

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

        val button0 = buildButton { }.asInternalComponent()
        val button1 = buildButton {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button1)

        assertThat(target.findNext()).isEqualTo(root)
    }

    @Test
    fun When_the_first_component_is_focused_Then_the_previous_focus_is_the_root() {

        val button0 = buildButton { }.asInternalComponent()
        val button1 = buildButton {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button0)

        assertThat(target.findPrevious()).isEqualTo(root)
    }

    @Test
    fun When_the_root_is_focused_Then_the_next_focus_is_the_first_component() {

        val button0 = buildButton { }.asInternalComponent()
        val button1 = buildButton {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        assertThat(target.findNext()).isEqualTo(button0)
    }

    @Test
    fun When_the_root_is_focused_Then_the_previous_focus_is_the_last_component() {

        val button0 = buildButton { }.asInternalComponent()
        val button1 = buildButton {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        assertThat(target.findPrevious()).isEqualTo(button1)
    }

    @Test
    fun When_the_root_has_only_one_child_Then_focus_order_is_correct() {

        val button0 = buildButton { }.asInternalComponent()

        root.addComponent(button0)
        target.refreshFocusables()

        assertThat(target.findNext()).isEqualTo(button0)
        target.focus(button0)
        assertThat(target.findNext()).isEqualTo(root)
    }
}
