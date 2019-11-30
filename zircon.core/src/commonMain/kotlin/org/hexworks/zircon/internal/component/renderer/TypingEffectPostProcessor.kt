package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.Delay

data class TypingEffectPostProcessor<T : Component>(private val baseDelayInMs: Long) : ComponentPostProcessor<T> {

    override fun render(tileGraphics: TileGraphics, context: ComponentPostProcessorContext<T>) {
        val width = tileGraphics.size.width
        tileGraphics.size.fetchPositions().forEach { position ->
            val (x, y) = position
            val delay = Delay(baseDelayInMs * width * y + baseDelayInMs * x)
            tileGraphics.getTileAt(position).map {
                tileGraphics.draw(it.withAddedModifiers(delay), position)
            }
        }
    }
}
