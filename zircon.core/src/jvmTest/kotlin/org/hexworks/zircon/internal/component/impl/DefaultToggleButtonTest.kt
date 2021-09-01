package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.ACTIVE
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.data.ComponentState.HIGHLIGHTED
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST", "TestFunctionName")
class DefaultToggleButtonTest : FocusableComponentImplementationTest<DefaultToggleButton>() {


    override lateinit var target: DefaultToggleButton

    override lateinit var graphics: TileGraphics

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.accentColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build()
            )
            .withHighlightedStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.primaryBackgroundColor)
                    .withBackgroundColor(DEFAULT_THEME.accentColor)
                    .build()
            )
            .withFocusedStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                    .withBackgroundColor(DEFAULT_THEME.accentColor)
                    .build()
            )
            .withActiveStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                    .withBackgroundColor(DEFAULT_THEME.accentColor)
                    .build()
            )
            .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultToggleButtonRenderer())
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_15X1).build()
        target = DefaultToggleButton(
            componentMetadata = ComponentMetadata(
                size = SIZE_15X1,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<ToggleButton>
            ),
            textProperty = TEXT.toProperty(),
            initialSelected = false
        )
        rendererStub.render(graphics, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAssignStyleSetForUnselectedState() {
        target.selectedProperty.value = false
        assertThat(target.componentState)
            .isEqualTo(UNSELECTED_ACTION)
    }

    @Test
    fun shouldProperlyAddButtonText() {
        val offset = target.contentOffset.x + DefaultToggleButtonRenderer.DECORATION_WIDTH
        TEXT.forEachIndexed { i, char ->
            assertThat(graphics.getTileAtOrNull(Position.create(i + offset, 0)))
                .isEqualTo(
                    TileBuilder.newBuilder()
                        .withCharacter(char)
                        .withStyleSet(target.componentStyleSet.fetchStyleFor(DEFAULT))
                        .build()
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
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    override fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
            phase = UIEventPhase.TARGET
        )
        rendererStub.clear()
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
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    companion object {
        val SIZE_15X1 = Size.create(15, 1)
        const val TEXT = "Button text"
        private val SELECTED_ACTION = HIGHLIGHTED
        private val UNSELECTED_ACTION = DEFAULT
    }
}
