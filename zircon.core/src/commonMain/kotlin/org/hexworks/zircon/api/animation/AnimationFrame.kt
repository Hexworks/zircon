package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.animation.InternalAnimationFrame

/**
 * Stores information about a single animation frame.
 */

public interface AnimationFrame {

    public val size: Size
    public val layers: List<Layer>

    /**
     * How many times this frame will be repeated.
     */
    public val repeatCount: Int

}
