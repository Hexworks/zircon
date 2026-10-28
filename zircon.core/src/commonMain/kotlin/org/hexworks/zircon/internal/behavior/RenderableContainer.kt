package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.internal.graphics.Renderable

/**
 * Contains a list of [Renderable] objects. Can be used by a renderer
 * for rendering tiles on the user's display.
 */
public interface RenderableContainer {

    /**
     * Contains the [Renderable] objects ordered from bottom to top.
     */
    public val renderables: List<Renderable>
}
