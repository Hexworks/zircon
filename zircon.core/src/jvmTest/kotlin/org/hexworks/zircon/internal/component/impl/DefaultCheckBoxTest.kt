package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
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
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST", "TestFunctionName")
class DefaultCheckBoxTest : FocusableComponentImplementationTest<DefaultCheckBox>() {

    override lateinit var target: DefaultCheckBox
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.accentColor
                backgroundColor = transparent()
            }
            highlightedStyle = styleSet {
                foregroundColor = DEFAULT_THEME.primaryBackgroundColor
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
            disabledStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = transparent()
            }
        }

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultCheckBoxRenderer())
        drawWindow = tileGraphics {
            size = SIZE_20X1
        }.toDrawWindow()
        target = DefaultCheckBox(
            componentMetadata = ComponentMetadata(
                size = SIZE_20X1,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<CheckBox>
            ),
            textProperty = TEXT.toProperty()
        )
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAddCheckBoxText() {
        val offset = 4
        TEXT.forEachIndexed { i, char ->
            assertThat(drawWindow.getTileAtOrNull(Position.create(i + offset, 0)))
                .isEqualTo(
                    characterTile {
                        character = char
                        styleSet = target.componentStyleSet.fetchStyleFor(DEFAULT)
                    }
                )
        }
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        val result = target.focusGiven()

        assertThat(result).isEqualTo(Processed)
        assertThat(target.componentState).isEqualTo(FOCUSED)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()

        assertThat(target.componentState).isEqualTo(DEFAULT)
    }

    @Test
    override fun When_a_focused_component_is_activated_Then_it_becomes_active() {

        target.focusGiven()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    override fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    override fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    companion object {
        val SIZE_20X1 = Size.create(20, 1)
        const val TEXT = "Button text"
    }
}
