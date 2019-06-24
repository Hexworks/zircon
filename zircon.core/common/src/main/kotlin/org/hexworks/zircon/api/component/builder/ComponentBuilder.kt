package org.hexworks.zircon.api.component.builder

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.alignmentAround
import org.hexworks.zircon.api.extensions.alignmentWithin
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> {

    /**
     * Sets the [ComponentStyleSet] the [Component] will use.
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U

    /**
     * Sets the [Tileset] to use for the [Component].
     */
    fun withTileset(tileset: TilesetResource): U

    /**
     * Sets the [AlignmentStrategy] to use for the [Component].
     */
    fun withAlignment(alignmentStrategy: AlignmentStrategy): U

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     * The component will be decorated with the given decorators in the given
     * order.
     */
    fun withDecorations(vararg renderers: ComponentDecorationRenderer): U

    /**
     * Sets the [ComponentRenderer] for the resulting [Component].
     */
    fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(size: Size): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(width: Int, height: Int): U = withSize(Sizes.create(width, height))

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(position))`
     */
    fun withPosition(position: Position): U = withAlignment(positionalAlignment(position))

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(x, y))`
     */
    fun withPosition(x: Int, y: Int): U = withPosition(Positions.create(x, y))

    /**
     * Aligns the resulting [Component] within the [tileGrid] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(tileGrid, alignment))`
     */
    fun withAlignmentWithin(tileGrid: TileGrid, alignment: ComponentAlignment): U =
            withAlignment(alignmentWithin(tileGrid, alignment))

    /**
     * Aligns the resulting [Component] within the [container] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(container, alignment))`
     */
    fun withAlignmentWithin(container: Container, alignment: ComponentAlignment): U =
            withAlignment(alignmentWithin(container, alignment))

    /**
     * Aligns the resulting [Component] around the [component] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(container, alignment))`
     */
    fun withAlignmentAround(component: Component, alignment: ComponentAlignment): U =
            withAlignment(alignmentAround(component, alignment))

}
