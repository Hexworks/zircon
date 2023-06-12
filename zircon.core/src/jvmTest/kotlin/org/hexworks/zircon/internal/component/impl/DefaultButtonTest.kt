package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.Button
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
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_ENTERED
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultButtonTest : FocusableComponentImplementationTest<DefaultButton>() {

    override lateinit var target: DefaultButton
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
        rendererStub = ComponentRendererStub(DefaultButtonRenderer())
        drawWindow = tileGraphics {
            size = SIZE_15X1
        }.toDrawWindow()
        target = DefaultButton(
            componentMetadata = ComponentMetadata(
                size = SIZE_15X1,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<Button>
            ),
            textProperty = TEXT.toProperty()
        )
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAddButtonText() {
        TEXT.forEachIndexed { i, char ->
            println("idx: $i")
            assertThat(drawWindow.getTileAtOrNull(Position.create(i, 0)))
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
    fun shouldProperlyHandleMousePress() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.defaultPosition()),
            phase = UIEventPhase.TARGET
        )
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.focusGiven()
        target.activated()
        target.mouseReleased(
            event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.defaultPosition()),
            phase = UIEventPhase.TARGET
        )

        assertThat(target.componentState).isEqualTo(HIGHLIGHTED)
    }

    companion object {
        val SIZE_15X1 = Size.create(15, 1)
        const val TEXT = "Button text"
    }
}
