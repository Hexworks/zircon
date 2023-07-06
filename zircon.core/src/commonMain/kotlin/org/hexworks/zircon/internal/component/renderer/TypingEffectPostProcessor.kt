package org.hexworks.zircon.internal.component.renderer

import korlibs.time.DateTime
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

/**
 * This [ComponentPostProcessor] adds a typing effect to the rendering of a component. Note that the Delay modifier
 * can't be used in this case because the changes made by post processors are not persisted, they are generated
 * on the fly instead.
 */
data class TypingEffectPostProcessor<T : Component>(private val baseDelayInMs: Long) : ComponentPostProcessor<T> {

    private var shouldShow = false
    private var startAt: Long = -1

    override fun render(drawWindow: DrawWindow, context: ComponentPostProcessorContext<T>) {
        if (startAt < 0) {
            startAt = DateTime.nowUnixMillisLong() + baseDelayInMs
        }
        val now = DateTime.nowUnixMillisLong()
        if (now > startAt) {
            shouldShow = true
        }
        val width = drawWindow.size.width
        drawWindow.transform { (x, y), tile ->
            val delay = baseDelayInMs * width * y + baseDelayInMs * x
            if (now > startAt + delay) tile else Tile.empty()
        }
    }
}
