package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.Color.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("TestFunctionName", "UNCHECKED_CAST")
class DefaultRadioButtonTest : FocusableComponentImplementationTest<DefaultRadioButton>() {

    override lateinit var target: DefaultRadioButton
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.accentColor
                backgroundColor = Color.TRANSPARENT
            }
            highlightedStyle = styleSet {
                backgroundColor = DEFAULT_THEME.accentColor
            }
            focusedStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryBackgroundColor
                backgroundColor = DEFAULT_THEME.accentColor
            }
            activeStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = DEFAULT_THEME.accentColor
            }
        }


    @BeforeTest
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultRadioButtonRenderer())
        drawWindow = tileGraphics {
            size = SIZE_20X1
        }.toDrawWindow()
        target = DefaultRadioButton(
            componentMetadata = ComponentMetadata(
                size = SIZE_20X1,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<RadioButton>
            ),
            initialText = DefaultCheckBoxTest.TEXT,
            key = "key"
        )
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = tileGraphics {
            size = SIZE_20X1
            tileset = TILESET_REX_PAINT_20X20
        }
        target.render(surface)
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            surface.getTileAtOrNull(Position.create(i + offset, 0)) shouldBe
                characterTile {
                    character = char
                    styleSet = target.componentStyleSet.fetchStyleFor(DEFAULT)
                }
        }
    }

    @Test
    fun shouldProperlyReturnText() {
        target.text shouldBe TEXT
    }

    @Test
    fun shouldAcceptFocus() {
        target.acceptsFocus() shouldBe true
    }

    @Test
    fun shouldProperlyGiveFocus() {
        val result = target.focusGiven()

        result shouldBe Processed
        target.componentState shouldBe FOCUSED
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()

        target.componentState shouldBe DEFAULT
    }

    @Test
    fun shouldProperlySelect() {
        target.isSelected = true

        rendererStub.render(drawWindow, ComponentRenderContext(target))

        getButtonChar() shouldBe 'O'
        target.isSelected shouldBe true
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        target.isSelected = true
        target.isSelected = false

        target.isSelected shouldBe false
        getButtonChar() shouldBe ' '
        target.componentState shouldBe DEFAULT
    }

    @Test
    override fun When_a_focused_component_is_activated_Then_it_becomes_active() {

        target.focusGiven()
        rendererStub.clear()
        target.activated()

        target.componentState shouldBe ACTIVE
    }

    @Test
    override fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
        target.activated()

        target.componentState shouldBe ACTIVE
    }

    @Test
    override fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, ),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        rendererStub.clear()
        target.activated()

        target.componentState shouldBe ACTIVE
    }

    @Test
    override fun When_the_mouse_is_released_on_a_focused_component_Then_it_becomes_highlighted() {

        target.focusGiven()
        rendererStub.clear()
        target.mouseReleased(
            event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, ),
            phase = UIEventPhase.TARGET
        )

        target.componentState shouldBe HIGHLIGHTED
    }

    private fun getButtonChar() = drawWindow.getTileAtOrNull(Position.create(1, 0))?.asCharacterTileOrNull()?.character

    companion object {
        val SIZE_20X1 = Size.create(20, 1)
        const val TEXT = "Button text"
    }
}
