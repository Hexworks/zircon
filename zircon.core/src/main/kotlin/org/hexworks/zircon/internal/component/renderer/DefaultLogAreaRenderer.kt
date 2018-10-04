package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.log.LogTextElement

class DefaultLogAreaRenderer : ComponentRenderer<LogArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        context.component.getLogElementBuffer().clearLogRenderPositions()

        val style = context.componentStyle.currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val logElements = component.getLogElementBuffer().getAllLogElements()
        var currentLogElementY = 0
        var currentScreenPosY = 0
        tileGraphics.clear()
        var delayTimeInMs = if (isTypewriterEffectIsSupported(context))
            context.component.delayInMsForTypewriterEffect!!.toLong()
        else
            0L

        logElements.forEach { element ->
            val result = element.render(tileGraphics,
                    LogElementRenderContext(context, PositionRenderContext(currentScreenPosY, currentLogElementY), delayTimeInMs))
            currentScreenPosY = result.currentScreenPosY
            currentLogElementY = element.getPosition().y
            delayTimeInMs = result.delayTimeInMs
        }
    }

    private fun isTypewriterEffectIsSupported(context: ComponentRenderContext<LogArea>) =
            (context.component.delayInMsForTypewriterEffect != null
                    && context.component.getLogElementBuffer().getAllLogElements().all { it is LogTextElement })


}

data class VisibleRenderArea(val topLeft: Position, val size: Size) {
    fun contains(position: Position): Boolean {
        return topLeft.x <= position.x && position.x + size.width() >= position.x
                && topLeft.y <= position.y && topLeft.y + size.height() >= topLeft.y

    }
}

data class LogElementRenderContext(val componentRenderContext: ComponentRenderContext<LogArea>,
                                   val positionRenderContext: PositionRenderContext, val delayTimeInMs: Long)

data class LogElementRenderResult(val currentScreenPosY: Int, val delayTimeInMs: Long = 0)
data class PositionRenderContext(val currentScreenPosY: Int, val currentLogElementPosY: Int)