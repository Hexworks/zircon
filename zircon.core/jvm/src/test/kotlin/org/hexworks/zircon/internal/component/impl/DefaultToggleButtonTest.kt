package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.input.MouseActionType.MOUSE_PRESSED
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultToggleButtonTest : ComponentImplementationTest<DefaultToggleButton>() {


    override lateinit var target: DefaultToggleButton

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.accentColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.primaryBackgroundColor)
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .withActiveStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(DEFAULT_THEME.accentColor)
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultToggleButtonRenderer())
        target = DefaultToggleButton(
                componentMetadata = ComponentMetadata(
                        size = SIZE_15X1,
                        position = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<ToggleButton>),
                initialText = TEXT)
    }

    @Test
    fun shouldProperlyAssignStyleSetForSelectState() {
        target.selectedProperty.value = true
        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(SELECTED_ACTION)
    }

    @Test
    fun shouldProperlyAssignStyleSetForUnselectedState() {
        target.selectedProperty.value = false
        assertThat(target.componentStyleSet.currentState())
                .isEqualTo(UNSELECTED_ACTION)
    }

    @Test
    fun shouldProperlyAddButtonText() {
        val surface = target.graphics
        val offset = target.contentPosition.x
        TEXT.forEachIndexed { i, char ->
            assertThat(surface.getTileAt(Position.create(i + offset, 0)).get())
                    .isEqualTo(TileBuilder.newBuilder()
                            .withCharacter(char)
                            .withStyleSet(target.componentStyleSet.fetchStyleFor(DEFAULT))
                            .build())
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
        val result = target.giveFocus()

        assertThat(result).isTrue()
        assertThat(target.componentStyleSet.currentState()).isEqualTo(FOCUSED)
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.takeFocus()

        assertThat(target.componentStyleSet.currentState()).isEqualTo(DEFAULT)
    }

    @Test
    fun shouldProperlyHandleMousePress() {
        target.mousePressed(MouseAction(MOUSE_PRESSED, 1, Position.defaultPosition()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(SELECTED_ACTION)
    }

    override fun shouldProperlyHandleMousePressed() {
        rendererStub.clear()

        target.mousePressed(MouseAction(MOUSE_PRESSED, 1, Position.zero()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(SELECTED_ACTION)
        assertThat(rendererStub.renderings.size).isEqualTo(1)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.mouseReleased(MouseAction(MouseActionType.MOUSE_RELEASED, 1, Position.defaultPosition()))

        assertThat(target.componentStyleSet.currentState()).isEqualTo(MOUSE_OVER)
    }

    companion object {
        val SIZE_15X1 = Size.create(15, 1)
        const val TEXT = "Button text"
        private val SELECTED_ACTION = MOUSE_OVER
        private val UNSELECTED_ACTION = DEFAULT
    }
}
