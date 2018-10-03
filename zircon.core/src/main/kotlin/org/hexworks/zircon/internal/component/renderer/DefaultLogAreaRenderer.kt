package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics

class DefaultLogAreaRenderer : ComponentRenderer<LogArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        context.component.getLogElementBuffer().clearLogRenderPositions()

        val style = context.componentStyle().currentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val logElements = component.getLogElementBuffer().getAllLogElements()
        var currentLogElementY = 0
        var currentY = 0
        tileGraphics.clear()
        logElements.forEach { element ->
            currentY = element.render(tileGraphics, context, PositionRenderContext(currentY, currentLogElementY))
            currentLogElementY = element.getPosition().y
        }
    }
}

data class VisibleRenderArea(val topLeft: Position, val size: Size) {
    fun contains(position: Position): Boolean {
        return topLeft.x <= position.x && position.x + size.width() >= position.x
                && topLeft.y <= position.y && topLeft.y + size.height() >= topLeft.y

    }
}

data class PositionRenderContext(val currentScreenPosY: Int, val currentLogElementPosY: Int)