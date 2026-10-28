package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.internal.extensions.asInternalComponent
import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.extensions.addComponent
import org.hexworks.zircon.api.component.extensions.addComponents
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.bottomLeftOf
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.ComponentRendererStub
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.component.impl.DefaultRootContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.test.BeforeTest
import kotlin.test.Test


@Suppress("TestFunctionName")
class DefaultComponentFocusOrderListTest {

    lateinit var rendererStub: ComponentRendererStub<RootContainer>
    lateinit var root: RootContainer
    lateinit var target: DefaultComponentFocusOrderList

    @BeforeTest
    fun setUp() {
        rendererStub = ComponentRendererStub()
        root = DefaultRootContainer(
            metadata = ComponentMetadata(
                position = Position.ZERO,
                size = Size(100, 100),
                tilesetProperty = CP437TilesetResources.aduDhabi16x16().toProperty(),
                componentStyleSetProperty = ComponentStyleSet.DEFAULT_STYLE.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                componentRenderer = rendererStub
            ),
            eventBus = EventBus.create(),
            eventScope = ZirconScope(),
        )
        target = DefaultComponentFocusOrderList(root)
    }

    @Test
    fun When_the_root_has_no_children_Then_next_is_the_root() {
        target.findNext() shouldBe root
    }

    @Test
    fun When_the_root_has_no_children_Then_previous_is_the_root() {
        target.findPrevious() shouldBe root
    }

    @Test
    fun When_the_root_has_children_Then_focus_order_is_the_flattened_tree() {

        val button0 = button { }.asInternalComponent()
        val box = vBox {
            preferredSize = Size(10, 2)
            position = bottomLeftOf(button0)
        }
        val button1a = button { }.asInternalComponent()
        val button1b = button { }.asInternalComponent()

        box.addComponents(button1a, button1b)
        root.addComponents(button0, box)

        target.refreshFocusables()

        target.findNext() shouldBe button0
        target.focus(root)
        target.findNext() shouldBe button0
        target.focus(button0)
        target.findNext() shouldBe button1a
        target.focus(button1a)
        target.findNext() shouldBe button1b
    }

    @Test
    fun When_the_last_component_is_focused_Then_the_next_focus_is_the_root() {

        val button0 = button { }.asInternalComponent()
        val button1 = button {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button1)

        target.findNext() shouldBe root
    }

    @Test
    fun When_the_first_component_is_focused_Then_the_previous_focus_is_the_root() {

        val button0 = button { }.asInternalComponent()
        val button1 = button {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(button0)

        target.findPrevious() shouldBe root
    }

    @Test
    fun When_the_root_is_focused_Then_the_next_focus_is_the_first_component() {

        val button0 = button { }.asInternalComponent()
        val button1 = button {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        target.findNext() shouldBe button0
    }

    @Test
    fun When_the_root_is_focused_Then_the_previous_focus_is_the_last_component() {

        val button0 = button { }.asInternalComponent()
        val button1 = button {
            position = bottomLeftOf(button0)
        }.asInternalComponent()

        root.addComponents(button0, button1)
        target.refreshFocusables()
        target.focus(root)

        target.findPrevious() shouldBe button1
    }

    @Test
    fun When_the_root_has_only_one_child_Then_focus_order_is_correct() {

        val button0 = button { }.asInternalComponent()

        root.addComponent(button0)
        target.refreshFocusables()

        target.findNext() shouldBe button0
        target.focus(button0)
        target.findNext() shouldBe root
    }
}
