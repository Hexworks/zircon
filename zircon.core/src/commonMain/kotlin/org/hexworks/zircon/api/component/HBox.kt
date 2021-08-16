package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.behavior.TitleOverride

/**
 * A [HBox] is a [Container] that automatically aligns elements added to it
 * horizontally (from left to right) and can contain a [title].
 * Note that the [title] will only be visible if the [HBox] is decorated with
 * a box.
 * @see ComponentDecorations
 * @see Container
 */
interface HBox : Container, TitleOverride {
    /**
     * The remaining (horizontal) space that's left in this component.
     */
    val remainingSpace: Int
}
