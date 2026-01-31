package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * A [ComponentDecorationRenderer] is responsible for rendering decorations for a given
 * component.
 */
interface ComponentDecorationRenderer : DecorationRenderer<ComponentDecorationRenderContext> {

    /**
     * The [Position] this renderer offsets the rendering. For example in the case of
     * a Box renderer which draws a box around the graphics the offset will be Position(1, 1)
     * because a box takes up 1 column and 1 row on the top and on the left.
     */
    val offset: Position

    /**
     * The [Size] this resulting decoration will occupy. For example a Box renderer
     * has an `occupiedSize` of `Size(2, 2)` since a box takes up 2 columns (left, right)
     * and 2 rows (top, down).
     */
    val occupiedSize: Size

    /**
     * When in [INTERACTIVE] mode the component decorations will react to [ComponentState]s.
     * In [NON_INTERACTIVE] mode they will use [ComponentState.DEFAULT] only.
     */
    enum class RenderingMode {
        INTERACTIVE, NON_INTERACTIVE
    }

    /**
     * Alignment for decorator attributes like box title.
     */
    enum class Alignment {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT
    }
}

internal fun Alignment.isLeft() = when (this) {
    Alignment.TOP_LEFT, Alignment.BOTTOM_LEFT -> true
    else -> false
}

internal fun Alignment.isRight() = when (this) {
    Alignment.TOP_RIGHT, Alignment.BOTTOM_RIGHT -> true
    else -> false
}

internal fun Alignment.isCenter() = when (this) {
    Alignment.TOP_CENTER, Alignment.BOTTOM_CENTER -> true
    else -> false
}

internal fun Alignment.isTop() = when (this) {
    Alignment.TOP_LEFT, Alignment.TOP_RIGHT, Alignment.TOP_CENTER -> true
    else -> false
}

internal fun Alignment.isBottom() = when (this) {
    Alignment.BOTTOM_LEFT, Alignment.BOTTOM_RIGHT, Alignment.BOTTOM_CENTER -> true
    else -> false
}
