package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.internal.graphics.Renderable

/**
 * Contains a list of [Renderable] objects. Can be used by a renderer
 * for rendering tiles on the screen.
 */
interface RenderableContainer {

    val renderables: List<Renderable>
}
