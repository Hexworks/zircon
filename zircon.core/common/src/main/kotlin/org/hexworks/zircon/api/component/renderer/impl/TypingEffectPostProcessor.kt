package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.modifier.Delay

data class TypingEffectPostProcessor<T : Component>(private val baseDelayInMs: Long) : ComponentPostProcessor<T> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentPostProcessorContext<T>) {
        val width = tileGraphics.size.width
        tileGraphics.size.fetchPositions().forEach { position ->
            val (x, y) = position
            val delay = Delay(baseDelayInMs * width * y + baseDelayInMs * x)
            tileGraphics.getTileAt(position).map {
                tileGraphics.setTileAt(position, it.withAddedModifiers(delay))
            }
        }
    }
}
