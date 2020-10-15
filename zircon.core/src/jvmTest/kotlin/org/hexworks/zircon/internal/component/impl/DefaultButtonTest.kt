package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_ENTERED
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultButtonTest : FocusableComponentImplementationTest<DefaultButton>() {

    override lateinit var target: DefaultButton
    override lateinit var graphics: TileGraphics

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
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultButtonRenderer())
        graphics =  DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_15X1).build()
        target = DefaultButton(
                componentMetadata = ComponentMetadata(
                        size = SIZE_15X1,
                        relativePosition = POSITION_2_3,
                        componentStyleSet = COMPONENT_STYLES,
                        tileset = TILESET_REX_PAINT_20X20),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = rendererStub as ComponentRenderer<Button>),
                initialText = TEXT)
        rendererStub.render(graphics, ComponentRenderContext(target))
    }

    @Test
    fun shouldProperlyAddButtonText() {
        TEXT.forEachIndexed { i, char ->
            println("idx: $i")
            assertThat(graphics.getTileAt(Position.create(i, 0)).get())
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
    fun shouldProperlyHandleMousePress() {
        target.mouseEntered(
                event = MouseEvent(MOUSE_ENTERED, 1, Position.defaultPosition()),
                phase = UIEventPhase.TARGET)
        target.activated()

        assertThat(target.componentState).isEqualTo(ACTIVE)
    }

    @Test
    fun shouldProperlyHandleMouseRelease() {
        target.focusGiven()
        target.activated()
        target.mouseReleased(
                event = MouseEvent(MouseEventType.MOUSE_RELEASED, 1, Position.defaultPosition()),
                phase = UIEventPhase.TARGET)

        assertThat(target.componentState).isEqualTo(HIGHLIGHTED)
    }

    companion object {
        val SIZE_15X1 = Size.create(15, 1)
        const val TEXT = "Button text"
    }
}
