package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ScrollDirection
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.behavior.extensions.scrollOneDown
import org.hexworks.zircon.api.behavior.extensions.scrollOneUp
import org.hexworks.zircon.api.behavior.extensions.scrollTo
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.ScrollDirection.VERTICAL
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.extensions.withY
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.util.isNavigationKey

public class DefaultVerticalScrollBar internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<ScrollBar>,
    internal val scrollable: Scrollable
) : ScrollBar, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    // The bar draws another component's scroll state, so it has to follow that.
    override val renderInputs: List<ObservableValue<*>>
        get() = listOf(scrollable.visibleOffsetProperty, scrollable.actualSizeProperty)

    public val actualHeight: Int
        get() = scrollable.actualSize.height

    public val actualWidth: Int
        get() = scrollable.actualSize.width

    public val scrollY: Int
        get() = scrollable.visibleOffset.y

    public val scrollX: Int
        get() = scrollable.visibleOffset.x

    public val contentHeight: Int
        get() = contentSize.height

    public val contentWidth: Int
        get() = contentSize.width

    /**
     * The ratio of the visible content area (vertical) to the actual size
     * of the [Scrollable].
     */
    public val contentRatioY: Double
        get() = scrollable.actualSize.height.toDouble() / contentHeight

    /**
     * The ratio of the visible content area (horizontal) to the actual size
     * of the [Scrollable].
     */
    public val contentRatioX: Double
        get() = scrollable.actualSize.width.toDouble() / contentWidth

    override val scrollDirection: ScrollDirection = VERTICAL

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = colorTheme.toInteractiveStyle()

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            if (event.isNavigationKey()) {
                // we don't want to override regular component navigation keys (<Tab>, <Shift>+<Tab> by default)
                Pass
            } else {
                val visibleOffset = scrollable.visibleOffset
                val height = scrollable.actualSize.height
                when (event.code) {
                    KeyCode.UP -> scrollable.scrollOneUp()
                    KeyCode.DOWN -> scrollable.scrollOneDown()
                    KeyCode.HOME -> scrollable.scrollTo(visibleOffset.withY(0))
                    KeyCode.END -> scrollable.scrollTo(visibleOffset.withY(height))
                    else -> Pass
                }
                Processed
            }
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            scrollToMouse(event)
            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            scrollToMouse(event)
            Processed
        } else Pass
    }

    /**
     * Scrolls so that the position under the cursor matches the cursor's place along the bar.
     * The cursor may be outside the bar: the dispatcher keeps delivering drags here after
     * it left, so the ratio is clamped to the bar's ends.
     */
    private fun scrollToMouse(event: MouseEvent) {
        val y = event.position.minus(position + contentOffset).y
        val percent = (y.toDouble() / contentSize.height).coerceIn(0.0, 1.0)
        val offset = scrollable.visibleOffset
        val height = scrollable.actualSize.height
        scrollable.scrollTo(offset.withY(height.times(percent).toInt()))
    }

}
