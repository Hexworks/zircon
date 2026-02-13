package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * A [ComponentDecorationRenderer] is responsible for rendering decorations for a given
 * component.
 *
 * ```
 * ╔═══╡ Box Decoration ╞═════════╗ <-- box offset: (1, 1) because it takes up 1 row
 * ║                              ║░    and 1 column relative to the top left corner
 * ║                              ║░    e.g.: the content starts at (1, 1)
 * ║                              ║░    size is 2x2 as a box takes up 2 rows and 2 columns
 * ║                              ║░    total
 * ║                              ║░
 * ║            content           ║░ <-- shadow offset: (0, 0) because it doesn't offset the content
 * ║                              ║░     it is rendered only on the bottom and on the right side
 * ║                              ║░     size is 1x1 however, as it takes up a row and a column
 * ║                              ║░
 * ║                              ║░
 * ╚══════════════════════════════╝░
 *  ░░░░░░░░Shadow decoration░░░░░░░
 * ```

 */
interface ComponentDecorationRenderer : DecorationRenderer<ComponentDecorationRenderContext> {

    /**
     * The [Position] this renderer offsets the rendering. For example, in the case of
     * a Box renderer which draws a box around the graphics, the offset will be Position(1, 1)
     * because a box takes up 1 column and 1 row on the top and on the left.
     */
    val offset: Position

    /**
     * The [Size] this resulting decoration will occupy. For example, a Box renderer
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

