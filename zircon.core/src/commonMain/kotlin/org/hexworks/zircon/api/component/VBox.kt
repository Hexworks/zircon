package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.behavior.TitleOverride

/**
 * A [VBox] is a [Container] that automatically aligns its child elements
 * vertically (from top to bottom).
 * Note that the [title] will only be visible if the [VBox] is decorated with
 * a box.
 * @see ComponentDecorations
 * @see Container
 */
interface VBox : Container, TitleOverride {
    /**
     * The remaining (vertical) space that's left in this component.
     */
    val remainingSpace: Int
}
