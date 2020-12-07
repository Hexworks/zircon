package org.hexworks.zircon.api.component.builder

import org.hexworks.zircon.api.ComponentAlignments.alignmentAround
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> {

    /**
     * Sets if the [Component] should be updated when it is attached to a parent
     * or not. By default the [Component]'s common properties ([ComponentProperties])
     * will be updated from its parent.
     */
    fun withUpdateOnAttach(updateOnAttach: Boolean): U

    /**
     * Sets the [ComponentStyleSet] the [Component] will use.
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U

    /**
     * Sets the [Tileset] to use for the [Component].
     */
    fun withTileset(tileset: TilesetResource): U

    /**
     * Sets the [ColorTheme] to use for the [Component].
     */
    fun withColorTheme(colorTheme: ColorTheme): U

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
     * Creates a [ComponentRenderer] for the resulting [Component] using the
     * given component renderer [fn].
     */
    fun withRendererFunction(fn: (TileGraphics, ComponentRenderContext<T>) -> Unit): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(size: Size): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(width: Int, height: Int): U = withSize(Size.create(width, height))

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(position))`
     */
    fun withPosition(position: Position): U = withAlignment(positionalAlignment(position))

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(x, y))`
     */
    fun withPosition(x: Int, y: Int): U = withPosition(Position.create(x, y))

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
