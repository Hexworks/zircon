package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.ScrollDirection.VERTICAL
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.util.isNavigationKey

class DefaultVerticalScrollBar internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<ScrollBar>,
    internal val scrollable: Scrollable
) : ScrollBar, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    val actualHeight: Int
        get() = scrollable.actualSize.height

    val actualWidth: Int
        get() = scrollable.actualSize.width

    val scrollY: Int
        get() = scrollable.visibleOffset.y

    val scrollX: Int
        get() = scrollable.visibleOffset.x

    val contentHeight: Int
        get() = contentSize.height

    val contentWidth: Int
        get() = contentSize.width

    /**
     * The ratio of the visible content area (vertical) to the actual size
     * of the [Scrollable].
     */
    val contentRatioY: Double
        get() = scrollable.actualSize.height.toDouble() / contentHeight

    /**
     * The ratio of the visible content area (horizontal) to the actual size
     * of the [Scrollable].
     */
    val contentRatioX: Double
        get() = scrollable.actualSize.width.toDouble() / contentWidth

    override val scrollDirection = VERTICAL

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractiveStyle()

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
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

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            val percent = event.position.y.toDouble() / contentSize.height
            val offset = scrollable.visibleOffset
            val height = scrollable.actualSize.height
            scrollable.scrollTo(offset.withY(height.times(percent).toInt()))
            return Processed
        } else Pass
    }

}
