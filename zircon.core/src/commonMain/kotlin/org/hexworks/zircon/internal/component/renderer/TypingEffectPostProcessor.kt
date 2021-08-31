package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.platform.util.SystemUtils

/**
 * This [ComponentPostProcessor] adds a typing effect to the rendering of a component. Note that the Delay modifier
 * can't be used in this case because the changes made by post processors are not persisted, they are generated
 * on the fly instead.
 */
data class TypingEffectPostProcessor<T : Component>(private val baseDelayInMs: Long) : ComponentPostProcessor<T> {

    private var shouldShow = false
    private var startAt: Long = -1

    override fun render(tileGraphics: TileGraphics, context: ComponentPostProcessorContext<T>) {
        if (startAt < 0) {
            startAt = SystemUtils.getCurrentTimeMs() + baseDelayInMs
        }
        val now = SystemUtils.getCurrentTimeMs()
        if (now > startAt) {
            shouldShow = true
        }
        val width = tileGraphics.size.width
        tileGraphics.transform { (x, y), tile ->
            val delay = baseDelayInMs * width * y + baseDelayInMs * x
            if (now > startAt + delay) tile else Tile.empty()
        }
    }
}
