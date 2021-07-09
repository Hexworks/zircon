package org.hexworks.zircon.api.component.builder

import org.hexworks.zircon.api.ComponentAlignments.alignmentAround
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.ComponentProperties
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

@Suppress("UNCHECKED_CAST")
interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> : Builder<T> {

    var name: String
    var position: Position
    var alignment: AlignmentStrategy
    var colorTheme: ColorTheme?

    /**
     * Shorthand for `decorations = listOf(decoration)`
     */
    var decoration: ComponentDecorationRenderer?

    /**
     * Sets the decorations that should be used for the component.
     * This also forces a recalculation of [contentSize]
     */
    var decorations: List<ComponentDecorationRenderer>

    /**
     * Returns the [title] or an empty string if it is not present.
     */
    val title: String
    var componentStyleSet: ComponentStyleSet
    var tileset: TilesetResource
    var updateOnAttach: Boolean
    var componentRenderer: ComponentRenderer<out T>
    var renderFunction: (TileGraphics, ComponentRenderContext<T>) -> Unit
    var preferredSize: Size
    var preferredContentSize: Size

    val contentSize: Size

    val contentWidth: Int
        get() = contentSize.width

    val contentHeight: Int
        get() = contentSize.height

    /**
     * The final [Size] of the [Component] that is being built. Size is calculated this way:
     * - If [preferredSize] is set it will be used
     * - If [preferredSize] is not set but [preferredContentSize] is set, then it will be calculated
     *   based on [preferredContentSize] and the size of the [decorations]
     * - If neither one of those are set size will be calculated based on [contentSize] and the size
     *   of [decorations]
     */
    val size: Size

    fun withName(name: String): U {
        this.name = name
        return this as U
    }

    /**
     * Sets if the [Component] should be updated when it is attached to a parent
     * or not. By default the [Component]'s common properties ([ComponentProperties])
     * will be updated from its parent.
     */
    fun withUpdateOnAttach(updateOnAttach: Boolean): U {
        this.updateOnAttach = updateOnAttach
        return this as U
    }

    /**
     * Sets the [ComponentStyleSet] the [Component] will use.
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U {
        this.componentStyleSet = componentStyleSet
        return this as U
    }

    /**
     * Sets the [Tileset] to use for the [Component].
     */
    fun withTileset(tileset: TilesetResource): U {
        this.tileset = tileset
        return this as U
    }

    /**
     * Sets the [ColorTheme] to use for the [Component].
     */
    fun withColorTheme(colorTheme: ColorTheme): U {
        this.colorTheme = colorTheme
        return this as U
    }

    /**
     * Sets the [AlignmentStrategy] to use for the [Component].
     */
    fun withAlignment(alignmentStrategy: AlignmentStrategy): U {
        this.alignment = alignmentStrategy
        return this as U
    }

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     * The component will be decorated with the given decorators in the given
     * order.
     */
    fun withDecorations(vararg renderers: ComponentDecorationRenderer): U {
        this.decorations = renderers.asList()
        return this as U
    }

    /**
     * Sets the [ComponentRenderer] for the resulting [Component].
     */
    fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U {
        this.componentRenderer = componentRenderer
        return this as U
    }

    /**
     * Creates a [ComponentRenderer] for the resulting [Component] using the
     * given component renderer [fn].
     */
    fun withRendererFunction(fn: (TileGraphics, ComponentRenderContext<T>) -> Unit): U {
        this.renderFunction = fn
        return this as U
    }

    /**
     * Sets the [Size] of the resulting [Component].
     */
    @Deprecated("The name is misleading, use preferred size instead", ReplaceWith("withPreferredSize(size)"))
    fun withSize(size: Size): U = withPreferredSize(size)

    /**
     * Sets the [Size] of the resulting [Component].
     */
    @Deprecated("The name is misleading, use preferred size instead", ReplaceWith("withPreferredSize(width, height)"))
    fun withSize(width: Int, height: Int): U = withSize(Size.create(width, height))

    /**
     * Sets the preferred [Size] of the resulting [Component].
     * The preferred size contains the content and the decorations.
     */
    fun withPreferredSize(size: Size): U {
        preferredSize = size
        return this as U
    }

    /**
     * Shorthand for [withPreferredSize].
     */
    fun withPreferredSize(width: Int, height: Int): U = withPreferredSize(Size.create(width, height))

    /**
     * Sets the preferred content [Size] of the resulting [Component].
     * The preferred content size contains the content only and doesn't contain the decorations.
     */
    fun withPreferredContentSize(size: Size): U {
        preferredContentSize = size
        return this as U
    }

    fun withPreferredContentSize(width: Int, height: Int): U = withPreferredSize(Size.create(width, height))

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
