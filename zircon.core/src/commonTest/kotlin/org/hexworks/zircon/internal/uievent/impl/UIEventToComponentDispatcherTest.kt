package org.hexworks.zircon.internal.uievent.impl

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.component.CheckBox.CheckBoxState
import org.hexworks.zircon.api.component.RadioButton.RadioButtonState
import org.hexworks.zircon.api.builder.component.horizontalSlider
import org.hexworks.zircon.api.builder.component.verticalSlider
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelative
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.DefaultComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultRootContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * Mouse capture: the component that got the press keeps getting the drags and the release,
 * wherever the cursor goes, and nothing else gets hover events meanwhile (the same implicit
 * capture Compose, JavaFX and Qt do).
 */
class UIEventToComponentDispatcherTest {

    private lateinit var target: DefaultComponentContainer
    private lateinit var sliderHandle: AttachedComponent

    // A 10 wide slider at (5, 5) and a button at (5, 10), both on a 40x20 root.
    private val slider = horizontalSlider {
        minValue = 0
        maxValue = 100
        numberOfSteps = 10
        position = SLIDER_POSITION
    }
    private val vertical = verticalSlider {
        minValue = 0
        maxValue = 100
        numberOfSteps = 10
        position = VERTICAL_POSITION
    }
    private val button = button {
        +"Button"
        position = BUTTON_POSITION
    }
    private val checkBox = checkBox {
        +"Check"
        position = CHECK_BOX_POSITION
    }
    private val radio = radioButton {
        +"Radio"
        key = "radio"
        position = RADIO_POSITION
    }

    @BeforeTest
    fun setUp() {
        target = DefaultComponentContainer(
            DefaultRootContainer(
                metadata = ComponentMetadata(
                    position = Position.ZERO,
                    size = Size(40, 20),
                    tilesetProperty = CP437TilesetResources.rexPaint16x16().toProperty(),
                    componentStyleSetProperty = ComponentStyleSet.DEFAULT_STYLE.toProperty()
                ),
                renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(),
                    componentRenderer = RootContainerRenderer()
                ),
                eventBus = EventBus.create(),
                eventScope = ZirconScope(),
            )
        )
        sliderHandle = target.addComponent(slider)
        target.addComponent(vertical)
        target.addComponent(button)
        target.addComponent(checkBox)
        target.addComponent(radio)
        target.activate()
    }

    @Test
    fun aDragThatLeftTheSliderStillMovesIt() {
        press(SLIDER_POSITION.withRelativeX(5))
        slider.currentValue shouldBe 50

        // Two rows below the slider, but still in the horizontal range.
        drag(SLIDER_POSITION.withRelative(Position(8, 2)))
        slider.currentValue shouldBe 80
    }

    @Test
    fun aDragPastTheEndsOfTheSliderClampsToTheEnds() {
        press(SLIDER_POSITION.withRelativeX(5))

        drag(Position(39, 19))
        slider.currentValue shouldBe 100

        drag(Position(0, 0))
        slider.currentValue shouldBe 0
    }

    @Test
    fun aVerticalDragAboveAndBelowTheSliderClampsToTheEnds() {
        press(VERTICAL_POSITION.withRelativeY(5))
        vertical.currentValue shouldBe 50

        drag(VERTICAL_POSITION.withRelativeY(15))
        vertical.currentValue shouldBe 100

        drag(Position(VERTICAL_POSITION.x, 0))
        vertical.currentValue shouldBe 0
    }

    @Test
    fun theSliderStaysActiveWhileDraggedOutsideItsBounds() {
        press(SLIDER_POSITION.withRelativeX(5))

        drag(BUTTON_POSITION)

        slider.state shouldBe ComponentState.ACTIVE
    }

    @Test
    fun nothingElseGetsHoverEventsWhileAPressIsCaptured() {
        var buttonEntered = 0
        button.handleMouseEvents(MOUSE_ENTERED) { _, _ -> buttonEntered++; Pass }
        press(SLIDER_POSITION.withRelativeX(5))

        drag(BUTTON_POSITION)

        buttonEntered shouldBe 0
        button.state shouldBe ComponentState.DEFAULT
    }

    @Test
    fun theDraggedOverComponentDoesNotGetTheDragOrTheRelease() {
        val buttonEvents = mutableListOf<MouseEventType>()
        button.handleMouseEvents(MOUSE_DRAGGED) { e, _ -> buttonEvents += e.type; Pass }
        button.handleMouseEvents(MOUSE_RELEASED) { e, _ -> buttonEvents += e.type; Pass }
        press(SLIDER_POSITION.withRelativeX(5))

        drag(BUTTON_POSITION)
        release(BUTTON_POSITION)

        buttonEvents shouldBe emptyList()
    }

    @Test
    fun releasingOutsideEndsTheCaptureAndResyncsHover() {
        press(SLIDER_POSITION.withRelativeX(5))
        drag(BUTTON_POSITION)

        release(BUTTON_POSITION)

        // Still focused (the press focused it), but no longer hovered.
        slider.state shouldBe ComponentState.FOCUSED
        button.state shouldBe ComponentState.HIGHLIGHTED
    }

    @Test
    fun releasingOverTheSliderAfterLeavingAndComingBackLeavesItHovered() {
        press(SLIDER_POSITION.withRelativeX(5))
        drag(BUTTON_POSITION)
        drag(SLIDER_POSITION.withRelativeX(7))

        release(SLIDER_POSITION.withRelativeX(7))

        slider.state shouldBe ComponentState.HIGHLIGHTED
    }

    @Test
    fun afterTheReleaseDragsAreNoLongerCaptured() {
        press(SLIDER_POSITION.withRelativeX(5))
        release(BUTTON_POSITION)
        val valueAfterRelease = slider.currentValue

        // A new press somewhere else starts a new gesture for that component.
        press(BUTTON_POSITION)
        drag(SLIDER_POSITION.withRelativeX(9))

        slider.currentValue shouldBe valueAfterRelease
    }

    @Test
    fun theCapturedComponentGetsExitedAndEnteredWhenCrossingItsOwnBounds() {
        val events = mutableListOf<MouseEventType>()
        button.handleMouseEvents(MOUSE_EXITED) { e, _ -> events += e.type; Pass }
        button.handleMouseEvents(MOUSE_ENTERED) { e, _ -> events += e.type; Pass }
        press(BUTTON_POSITION)

        drag(BUTTON_POSITION.withRelativeY(3))
        drag(BUTTON_POSITION.withRelativeY(4))
        drag(BUTTON_POSITION.withRelativeX(1))

        events shouldBe listOf(MOUSE_EXITED, MOUSE_ENTERED)
    }

    @Test
    fun aButtonDraggedOffItselfIsNoLongerActive() {
        press(BUTTON_POSITION)
        button.state shouldBe ComponentState.ACTIVE

        drag(BUTTON_POSITION.withRelativeY(3))

        button.state shouldNotBe ComponentState.ACTIVE
    }

    @Test
    fun releasingOverTheCapturedComponentLeavesItHovered() {
        press(BUTTON_POSITION)

        release(BUTTON_POSITION.withRelativeX(1))

        button.state shouldBe ComponentState.HIGHLIGHTED
    }

    @Test
    fun aDetachedCapturedComponentDropsTheCapture() {
        press(SLIDER_POSITION.withRelativeX(5))
        val value = slider.currentValue
        sliderHandle.detach()

        drag(SLIDER_POSITION.withRelativeX(9))
        release(SLIDER_POSITION.withRelativeX(9))

        slider.currentValue shouldBe value
    }

    // Slider state machine: focus and keys. Sliders are the first two focusables, so one
    // tab lands on the horizontal one.

    @Test
    fun tabbingOntoTheSliderFocusesIt() {
        key(KeyCode.TAB)

        slider.state shouldBe ComponentState.FOCUSED
    }

    @Test
    fun theActivationKeyDoesNothingToAFocusedSlider() {
        key(KeyCode.TAB)
        val value = slider.currentValue

        key(KeyCode.SPACE)

        slider.state shouldBe ComponentState.FOCUSED
        slider.currentValue shouldBe value
    }

    @Test
    fun anArrowKeyOnAFocusedSliderMovesItAndMakesItActive() {
        key(KeyCode.TAB)

        key(KeyCode.RIGHT)

        slider.currentValue shouldBe 1
        slider.state shouldBe ComponentState.ACTIVE
    }

    @Test
    fun tabbingAwayFromAnActiveSliderResetsItToDefault() {
        key(KeyCode.TAB)
        key(KeyCode.RIGHT)

        key(KeyCode.TAB)

        slider.state shouldBe ComponentState.DEFAULT
        vertical.state shouldBe ComponentState.FOCUSED
    }

    @Test
    fun pressingTheSliderMakesItActiveNotHighlighted() {
        press(SLIDER_POSITION.withRelativeX(5))

        slider.state shouldBe ComponentState.ACTIVE
    }

    @Test
    fun clickingSomethingElseTakesTheFocusAndResetsTheSlider() {
        press(SLIDER_POSITION.withRelativeX(5))
        release(SLIDER_POSITION.withRelativeX(5))

        press(BUTTON_POSITION)
        release(BUTTON_POSITION)

        slider.state shouldBe ComponentState.DEFAULT
    }

    @Test
    fun aSliderUsesTheSameStyleForFocusedAndActive() {
        val styles = (slider as InternalComponent).convertColorTheme(ColorThemeResource.CYBERPUNK.getTheme())

        styles.fetchStyleFor(ComponentState.FOCUSED) shouldBe styles.fetchStyleFor(ComponentState.ACTIVE)
        styles.fetchStyleFor(ComponentState.FOCUSED) shouldNotBe styles.fetchStyleFor(ComponentState.DEFAULT)
        styles.fetchStyleFor(ComponentState.FOCUSED) shouldNotBe styles.fetchStyleFor(ComponentState.HIGHLIGHTED)
    }

    // Check box and radio button: the pending glyph survives leaving the bounds, a release
    // inside commits, a release outside cancels.

    @Test
    fun aPressedCheckBoxKeepsShowingThePendingStateWhileTheCursorIsOutside() {
        press(CHECK_BOX_POSITION)
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKING

        drag(CHECK_BOX_POSITION.withRelativeY(3))
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKING

        drag(CHECK_BOX_POSITION.withRelativeX(1))
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKING
    }

    @Test
    fun releasingOnTheCheckBoxTogglesIt() {
        press(CHECK_BOX_POSITION)
        release(CHECK_BOX_POSITION)

        checkBox.isSelected shouldBe true
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKED
    }

    @Test
    fun releasingOutsideTheCheckBoxCancelsThePress() {
        press(CHECK_BOX_POSITION)
        drag(CHECK_BOX_POSITION.withRelativeY(3))

        release(CHECK_BOX_POSITION.withRelativeY(3))

        checkBox.isSelected shouldBe false
        checkBox.checkBoxState shouldBe CheckBoxState.UNCHECKED
    }

    @Test
    fun leavingAndComingBackBeforeReleasingStillTogglesTheCheckBox() {
        press(CHECK_BOX_POSITION)
        drag(CHECK_BOX_POSITION.withRelativeY(3))
        drag(CHECK_BOX_POSITION.withRelativeX(2))

        release(CHECK_BOX_POSITION.withRelativeX(2))

        checkBox.isSelected shouldBe true
    }

    @Test
    fun aCheckedBoxShowsUncheckingForTheWholePressToo() {
        checkBox.isSelected = true

        press(CHECK_BOX_POSITION)
        drag(CHECK_BOX_POSITION.withRelativeY(3))

        checkBox.checkBoxState shouldBe CheckBoxState.UNCHECKING
        release(CHECK_BOX_POSITION.withRelativeY(3))
        checkBox.isSelected shouldBe true
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKED
    }

    @Test
    fun theActivationKeyTogglesAFocusedCheckBoxOnRelease() {
        repeat(4) { key(KeyCode.TAB) }
        checkBox.state shouldBe ComponentState.FOCUSED

        key(KeyCode.SPACE)
        checkBox.checkBoxState shouldBe CheckBoxState.CHECKING

        keyUp(KeyCode.SPACE)
        checkBox.isSelected shouldBe true
    }

    @Test
    fun aPressedRadioButtonShowsPressedUntilTheRelease() {
        press(RADIO_POSITION)
        radio.state shouldBe RadioButtonState.PRESSED
        radio.isSelected shouldBe false

        drag(RADIO_POSITION.withRelativeY(3))
        radio.state shouldBe RadioButtonState.PRESSED

        drag(RADIO_POSITION.withRelativeX(1))
        release(RADIO_POSITION.withRelativeX(1))
        radio.state shouldBe RadioButtonState.SELECTED
        radio.isSelected shouldBe true
    }

    @Test
    fun releasingOutsideTheRadioButtonCancelsThePress() {
        press(RADIO_POSITION)
        drag(RADIO_POSITION.withRelativeY(3))

        release(RADIO_POSITION.withRelativeY(3))

        radio.isSelected shouldBe false
        radio.state shouldBe RadioButtonState.NOT_SELECTED
    }

    private val InternalComponent.state get() = componentState
    private val org.hexworks.zircon.api.component.Component.state get() = (this as InternalComponent).componentState

    private fun key(code: KeyCode) = target.dispatch(KeyboardEvent(KeyboardEventType.KEY_PRESSED, code, ""))
    private fun keyUp(code: KeyCode) = target.dispatch(KeyboardEvent(KeyboardEventType.KEY_RELEASED, code, ""))
    private fun press(position: Position) = target.dispatch(MouseEvent(MOUSE_PRESSED, 1, position))
    private fun drag(position: Position) = target.dispatch(MouseEvent(MOUSE_DRAGGED, 1, position))
    private fun release(position: Position) = target.dispatch(MouseEvent(MOUSE_RELEASED, 1, position))

    companion object {
        val SLIDER_POSITION = Position(5, 5)
        val VERTICAL_POSITION = Position(30, 2)
        val BUTTON_POSITION = Position(5, 10)
        val CHECK_BOX_POSITION = Position(5, 14)
        val RADIO_POSITION = Position(20, 14)
    }
}
