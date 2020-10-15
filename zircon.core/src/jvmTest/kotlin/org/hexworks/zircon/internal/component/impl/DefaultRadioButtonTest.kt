package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.ACTIVE
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName")
class DefaultRadioButtonTest : FocusableComponentImplementationTest<DefaultRadioButton>() {

    override lateinit var target: DefaultRadioButton
    override lateinit var graphics: TileGraphics

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.accentColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
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
        rendererStub = ComponentRendererStub(DefaultRadioButtonRenderer())
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_20X1).build()
        target = DefaultRadioButton(
                componentMetadata = ComponentMetadata(
                        size = SIZE_20X1,
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub),
                initialText = DefaultCheckBoxTest.TEXT,
                key = "key"
        )
        rendererStub.render(graphics, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAddRadioButtonText() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(SIZE_20X1)
                .withTileset(TILESET_REX_PAINT_20X20)
                .build()
        target.render(surface)
        val offset = 4
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
    fun shouldProperlySelect() {
        target.isSelected = true

        rendererStub.render(graphics, ComponentRenderContext(target))

        assertThat(getButtonChar()).isEqualTo('O')
        assertThat(target.isSelected).isTrue()
    }

    @Test
    fun shouldProperlyRemoveSelection() {
        target.isSelected = true
        target.isSelected = false

        assertThat(target.isSelected).isFalse()
        assertThat(getButtonChar()).isEqualTo(' ')
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
        target.mouseEntered(event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    override fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)
        target.focusGiven()
        rendererStub.clear()
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    override fun When_the_mouse_is_released_on_a_focused_component_Then_it_becomes_highlighted() {

        target.focusGiven()
        rendererStub.clear()
        target.mouseReleased(
                event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.zero()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(ComponentState.HIGHLIGHTED)
    }

    private fun getButtonChar() = graphics.getTileAt(Position.create(1, 0))
            .get().asCharacterTile().get().character

    companion object {
        val SIZE_20X1 = Size.create(20, 1)
        const val TEXT = "Button text"
    }
}
