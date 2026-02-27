package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase.BUBBLE
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("MemberVisibilityCanBePrivate", "TestFunctionName")
class DefaultComponentTest : CommonComponentTest<DefaultComponent>() {

    override lateinit var target: DefaultComponent

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSet.empty()

    lateinit var appliedColorTheme: ColorTheme

    @BeforeTest
    override fun setUp() {
        componentStub = ComponentStub(DefaultContainerTest.COMPONENT_STUB_POSITION_1x1, Size.create(2, 2))
        rendererStub = ComponentRendererStub()
        target = object : DefaultComponent(
            metadata = ComponentMetadata(
                size = DefaultContainerTest.SIZE_4x4,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderer = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub
            )
        ) {

            override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
                appliedColorTheme = colorTheme
                return ComponentStyleSet.empty()
            }

            override fun acceptsFocus(): Boolean {
                return false
            }

        }
    }

    @Test
    fun shouldMoveRightByProperly() {
        target.moveRightBy(2)

        target.relativePosition shouldBe POSITION_2_3.withRelativeX(2)
    }

    @Test
    fun shouldMoveLeftByProperly() {
        target.moveLeftBy(2)

        target.relativePosition shouldBe POSITION_2_3.withRelativeX(-2)
    }

    @Test
    fun shouldMoveUpByProperly() {
        target.moveUpBy(1)

        target.relativePosition shouldBe POSITION_2_3.withRelativeY(-1)
    }

    @Test
    fun shouldMoveDownByProperly() {
        target.moveDownBy(1)

        target.relativePosition shouldBe POSITION_2_3.withRelativeY(1)
    }

    @Test
    fun shouldMoveByProperly() {
        target.moveBy(Position.OFFSET_1X1)

        target.relativePosition shouldBe POSITION_2_3.withRelative(Position.OFFSET_1X1)
    }

    @Test
    fun shouldProperlyApplyStylesOnInit() {
        target.componentState shouldBe ComponentState.DEFAULT
    }

    @Test
    fun shouldProperlySetNewPosition() {
        target.moveTo(NEW_POSITION_6x7)

        target.position shouldBe NEW_POSITION_6x7
    }

    @Test
    fun shouldContainBoundableWhichIsContained() {
        target.containsBoundable(Boundable.create(POSITION_2_3, SIZE_4x4 - Size.one())) shouldBe true
    }

    @Test
    fun shouldNotContainBoundableWhichIsContained() {
        target.containsBoundable(Boundable.create(POSITION_2_3, SIZE_4x4 + Size.one())) shouldBe false
    }

    @Test
    fun shouldContainPositionWhichIsContained() {
        target.containsPosition(POSITION_2_3) shouldBe true
    }

    @Test
    fun shouldNotContainPositionWhichIsContained() {
        target.containsPosition(POSITION_2_3 - Position.OFFSET_1X1) shouldBe false
    }

    @Test
    fun shouldNotifyObserversWhenInputIsEmitted() {
        var notified = false

        target.handleMouseEvents(MOUSE_CLICKED) { _, _ ->
            notified = true
            Processed
        }

        target.process(
            event = MouseEvent(MOUSE_CLICKED, 1, Position.DEFAULT_POSITION),
            phase = BUBBLE
        )

        notified shouldBe true
    }

    @Test
    fun When_a_component_is_hovered_Then_it_has_mouse_over_style() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.DEFAULT_POSITION),
            phase = TARGET
        )

        target.componentState shouldBe ComponentState.HIGHLIGHTED
    }

    @Test
    fun When_a_component_is_no_longer_hovered_and_has_no_focus_Then_style_is_reset() {
        target.mouseEntered(
            event = MouseEvent(MOUSE_ENTERED, 1, Position.DEFAULT_POSITION),
            phase = TARGET
        )

        target.mouseExited(
            event = MouseEvent(MOUSE_EXITED, 1, Position.DEFAULT_POSITION),
            phase = TARGET
        )

        target.componentState shouldBe ComponentState.DEFAULT
    }

    @Test
    fun When_a_component_is_no_longer_hovered_and_has_focus_Then_style_is_reset() {
        target.focusGiven()

        target.mouseExited(
            event = MouseEvent(MOUSE_EXITED, 1, Position.DEFAULT_POSITION),
            phase = TARGET
        )

        target.componentState shouldBe ComponentState.FOCUSED
    }


    @Test
    fun shouldProperlyCalculateAbsolutePositionWithDeeplyNestedComponents() {

        val rootPos = Position.create(1, 1)
        val parentPos = Position.create(2, 1)
        val leafPos = Position.create(1, 2)

        val root = buildPanel {
            withPreferredSize {
                width = 10
                height = 10
            }
            position = rootPos
        }

        val parent = buildPanel {
            withPreferredSize {
                width = 7
                height = 7
            }
            position = parentPos
        }

        root.addComponent(parent)

        val leaf = buildLabel {
            +"foo"
            position = leafPos
        }
        parent.addComponent(leaf)

        leaf.absolutePosition shouldBe rootPos + parentPos + leafPos
    }

    @Test
    fun shouldProperlyListenToMousePress() {
        var pressed = false
        target.handleMouseEvents(MOUSE_PRESSED) { _, _ ->
            pressed = true
            Processed
        }

        target.process(
            event = MouseEvent(MOUSE_PRESSED, 1, POSITION_2_3),
            phase = BUBBLE
        )

        pressed shouldBe true
    }

    @Test
    fun shouldBeEqualToItself() {
        target shouldBe target
    }

    companion object {
        val POSITION_2_3 = Position.create(2, 3)
        val NEW_POSITION_6x7 = Position.create(6, 7)
        val SIZE_4x4 = Size.create(4, 4)
    }
}
