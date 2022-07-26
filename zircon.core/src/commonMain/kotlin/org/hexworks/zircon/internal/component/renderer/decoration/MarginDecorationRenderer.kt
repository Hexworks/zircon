package org.hexworks.zircon.internal.component.renderer.decoration

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

data class MarginDecorationRenderer(
    val top: Int,
    val right: Int,
    val bottom: Int,
    val left: Int,
    private val renderingMode: RenderingMode = NON_INTERACTIVE
) : ComponentDecorationRenderer {

    override val offset = Position.create(left, top)

    override val occupiedSize = Size.create(left + right, top + bottom)

    override fun render(tileGraphics: TileGraphics, context: ComponentDecorationRenderContext) {
    }
}
