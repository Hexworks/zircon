package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.internal.component.renderer.decoration.*

/**
 * This object contains functions for creating component decorations.
 */
object ComponentDecorations {

    /**
     * Can be used to draw a border around a [Component]. A border is
     * [RenderingMode.NON_INTERACTIVE] by default.
     */
    fun border(
        border: Border = Border.newBuilder().build(),
        renderingMode: RenderingMode = NON_INTERACTIVE
    ): ComponentDecorationRenderer = BorderDecorationRenderer(
        border = border,
        renderingMode = renderingMode
    )

    /**
     * Can be used to draw a box (using box drawing characters) around a [Component].
     * **Note that** the [title] will only be displayed for a [Component] if it is wrapped
     * with a box. This decoration uses [RenderingMode.NON_INTERACTIVE] by default.
     */
    fun box(
        boxType: BoxType = BoxType.SINGLE,
        title: String = "",
        renderingMode: RenderingMode = NON_INTERACTIVE,
        titleAlignment: Alignment = Alignment.TOP_LEFT
    ): ComponentDecorationRenderer = BoxDecorationRenderer(
        boxType = boxType,
        titleProperty = title.toProperty(),
        renderingMode = renderingMode,
        titleAlignment = titleAlignment
    )

    /**
     * Wraps a [Component] on the left and the right sides with the given
     * [leftSideCharacter] and [rightSideCharacter]. This decoration will use
     * [RenderingMode.INTERACTIVE] by default.
     */
    fun side(
        leftSideCharacter: Char = '[',
        rightSideCharacter: Char = ']',
        renderingMode: RenderingMode = RenderingMode.INTERACTIVE
    ): ComponentDecorationRenderer = SideDecorationRenderer(
        leftSideCharacter = leftSideCharacter,
        rightSideCharacter = rightSideCharacter,
        renderingMode = renderingMode
    )

    /**
     * Can be used to draw a shadow around a [Component]. The shadow is drawn
     * around the bottom and the right sides.
     */
    fun shadow(): ComponentDecorationRenderer = ShadowDecorationRenderer()

    /**
     * Can be used to add a half box decoration (half-height "border") to a [Component].
     */
    fun halfBlock(
        renderingMode: RenderingMode = NON_INTERACTIVE
    ): ComponentDecorationRenderer = HalfBlockDecorationRenderer(renderingMode)

    /**
     * Can be used to add margin to a [Component]. Padding is measured in tiles.
     * @param value the margin to add to all sides (top, right, bottom and left)
     */

    fun margin(
        value: Int
    ): ComponentDecorationRenderer = MarginDecorationRenderer(
        top = value,
        right = value,
        bottom = value,
        left = value
    )

    /**
     * Can be used to add margin to a [Component]. Padding is measured in tiles.
     */

    fun margin(
        top: Int,
        right: Int,
        bottom: Int,
        left: Int
    ): ComponentDecorationRenderer = MarginDecorationRenderer(
        top = top,
        right = right,
        bottom = bottom,
        left = left
    )

    /**
     * Can be used to add margin to a [Component]. Padding is measured in tiles.
     * @param x horizontal margin (left and right)
     * @param y vertical margin (top and bottom)
     */

    fun margin(
        y: Int,
        x: Int
    ): ComponentDecorationRenderer = MarginDecorationRenderer(
        top = y,
        right = x,
        bottom = y,
        left = x
    )

    /**
     * Can be used to explicitly state that a [Component] has no decorations. This is useful
     * when a [Component] has decorations by default.
     */

    fun noDecoration(): ComponentDecorationRenderer = MarginDecorationRenderer(
        top = 0,
        right = 0,
        bottom = 0,
        left = 0
    )


}
