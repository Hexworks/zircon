package org.hexworks.zircon.internal.component.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.BinarySelector.LabelAlignment
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState.ACTIVE
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("UNCHECKED_CAST", "TestFunctionName")
class DefaultBinarySelectorTest : FocusableComponentImplementationTest<DefaultBinarySelector>() {

    override lateinit var target: DefaultBinarySelector
    override lateinit var drawWindow: DrawWindow


    @BeforeTest
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultCheckBoxRenderer()) as ComponentRendererStub<DefaultBinarySelector>
        drawWindow = tileGraphics { size = SIZE_20X1 }.toDrawWindow()
        target = selector(allowUnselect = true)
        rendererStub.render(drawWindow, ComponentRenderContext(target))
    }

    private fun selector(allowUnselect: Boolean, selected: Boolean = false) = DefaultBinarySelector(
        componentMetadata = ComponentMetadata(
            size = SIZE_20X1,
            position = POSITION_2_3,
            componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
            tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
        ),
        renderingStrategy = DefaultComponentRenderingStrategy(
            decorationRenderers = listOf(),
            componentRenderer = rendererStub as ComponentRenderer<BinarySelector>
        ),
        textProperty = TEXT.toProperty(),
        key = "key",
        initialSelected = selected,
        allowUnselect = allowUnselect,
        labelAlignment = LabelAlignment.RIGHT,
    )

    private fun click() {
        target.activated()
        target.deactivated()
    }

    private fun leave() = target.mouseExited(
        event = MouseEvent(MouseEventType.MOUSE_EXITED, 1, Position.ZERO),
        phase = UIEventPhase.TARGET
    )

    private fun rendered(renderer: ComponentRenderer<BinarySelector>, width: Int = 20): String {
        val window = tileGraphics { size = Size(width, 1) }.toDrawWindow()
        renderer.render(window, ComponentRenderContext(target))
        return (0 until width).joinToString("") { x ->
            window.getTileAtOrNull(Position(x, 0))?.asCharacterTileOrNull()?.character?.toString() ?: " "
        }.trimEnd()
    }

    @Test
    fun shouldProperlyAddCheckBoxText() {
        val offset = DefaultCheckBoxRenderer.DECORATION_WIDTH
        TEXT.forEachIndexed { i, char ->
            drawWindow.getTileAtOrNull(Position(i + offset, 0)) shouldBe
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
        target.focusGiven() shouldBe Processed
        target.componentState shouldBe FOCUSED
    }

    @Test
    fun shouldProperlyTakeFocus() {
        target.focusTaken()
        target.componentState shouldBe DEFAULT
    }

    @Test
    override fun When_a_focused_component_is_activated_Then_it_becomes_active() {
        target.focusGiven()
        target.activated()
        target.componentState shouldBe ACTIVE
    }

    @Test
    override fun When_a_highlighted_component_without_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.ZERO),
            phase = UIEventPhase.TARGET
        )
        target.activated()
        target.componentState shouldBe ACTIVE
    }

    @Test
    override fun When_a_highlighted_component_with_focus_is_activated_Then_it_becomes_active() {
        target.mouseEntered(
            event = MouseEvent(MouseEventType.MOUSE_ENTERED, 1, Position.ZERO),
            phase = UIEventPhase.TARGET
        )
        target.focusGiven()
        target.activated()
        target.componentState shouldBe ACTIVE
    }

    // Behavior: the press is pending, the release commits, a release outside cancels.

    @Test
    fun a_press_is_pending_and_the_release_commits_it() {
        target.activated()
        target.isPressed shouldBe true
        target.isSelected shouldBe false
        target.deactivated()
        target.isPressed shouldBe false
        target.isSelected shouldBe true
    }

    @Test
    fun a_selector_that_allows_unselecting_toggles_on_every_click() {
        click()
        target.isSelected shouldBe true
        click()
        target.isSelected shouldBe false
    }

    @Test
    fun a_selector_that_does_not_allow_unselecting_stays_selected_when_clicked_again() {
        target = selector(allowUnselect = false)
        click()
        target.isSelected shouldBe true
        click()
        target.isSelected shouldBe true
    }

    @Test
    fun a_release_outside_cancels_the_press() {
        target.activated()
        leave()
        target.isPressed shouldBe true
        target.deactivated()
        target.isPressed shouldBe false
        target.isSelected shouldBe false
    }

    @Test
    fun losing_the_focus_drops_the_press() {
        target.activated()
        target.focusTaken()
        target.isPressed shouldBe false
        target.isSelected shouldBe false
    }

    // Looks: the three stock renderers read the pending and the selected state.

    @Test
    fun the_check_box_look_shows_the_pending_and_the_selected_state() {
        val renderer = DefaultCheckBoxRenderer()
        rendered(renderer) shouldBe "[ ] $TEXT"
        target.activated()
        rendered(renderer) shouldBe "[+] $TEXT"
        target.deactivated()
        rendered(renderer) shouldBe "[*] $TEXT"
        target.activated()
        rendered(renderer) shouldBe "[-] $TEXT"
    }

    @Test
    fun the_radio_button_look_shows_the_pending_and_the_selected_state() {
        val renderer = DefaultRadioButtonRenderer()
        rendered(renderer) shouldBe "< > $TEXT"
        target.activated()
        rendered(renderer) shouldBe "<o> $TEXT"
        target.deactivated()
        rendered(renderer) shouldBe "<O> $TEXT"
    }

    @Test
    fun the_toggle_button_look_puts_the_text_after_the_switch() {
        rendered(DefaultToggleButtonRenderer(), 15).substring(DefaultToggleButtonRenderer.DECORATION_WIDTH) shouldBe TEXT
    }

    // Building: the three DSL functions preset role and behavior, the key comes from the text.

    @Test
    fun the_builders_preset_the_role_and_the_behavior() {
        val check = checkBox { +"Music" }
        val radio = radioButton { +"Easy" }
        val toggle = toggleButton { +"Mipmapping" }
        check.role shouldBe ComponentRole.CHECK_BOX
        radio.role shouldBe ComponentRole.RADIO_BUTTON
        toggle.role shouldBe ComponentRole.TOGGLE_BUTTON
        check.allowUnselect shouldBe true
        radio.allowUnselect shouldBe false
        toggle.allowUnselect shouldBe true
    }

    @Test
    fun the_key_is_derived_from_the_text_unless_set() {
        radioButton { +"Go to the bar!" }.key shouldBe "go-to-the-bar"
        radioButton { +"Go to the bar!"; key = "bar" }.key shouldBe "bar"
        Selectable.keyFrom("  Ünïcode, 2 things ") shouldBe "ünïcode-2-things"
        shouldThrow<IllegalArgumentException> { checkBox { } }
        checkBox { key = "blank" }.key shouldBe "blank"
    }

    companion object {
        val SIZE_20X1 = Size(20, 1)
        const val TEXT = "Button text"
    }
}
