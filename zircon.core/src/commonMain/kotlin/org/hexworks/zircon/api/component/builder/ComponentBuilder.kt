package org.hexworks.zircon.api.component.builder

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.ComponentAlignments.alignmentAround
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.builder.Builder
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
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer

@Suppress("UNCHECKED_CAST")
interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> : Builder<T>, ComponentEventSource {

    /**
     * The name of this component. Not mandatory, but if present it will
     * be used for debug purposes.
     */
    var name: String

    /**
     * This [AlignmentStrategy] will be used then the component
     * is aligned within its parent.
     */
    var alignment: AlignmentStrategy

    /**
     * Sets the decorations that should be used for the component.
     * This also forces a recalculation of [contentSize]
     */
    var decorations: List<ComponentDecorationRenderer>

    /**
     * Sets whether the component's properties should be updated
     * when it is attached to a parent or not.
     */
    var updateOnAttach: Boolean

    /**
     * The renderer which will be used to render the resulting component.
     */
    var componentRenderer: ComponentRenderer<out T>

    /**
     * The [Size] allocated for the whole component.
     * **Note that** only one of [preferredSize] and [preferredContentSize]
     * can be set at any given time.
     * Encompasses the [contentSize] and the [occupiedSize].
     */
    var preferredSize: Size

    /**
     * The [Size] allocated for the **contents** of the resulting component.
     * **Note that** only one of [preferredSize] and [preferredContentSize]
     * can be set at any given time.
     * This doesn't contain [occupiedSize].
     */
    var preferredContentSize: Size

    // common properties
    var colorTheme: ColorTheme
    var componentStyleSet: ComponentStyleSet
    var tileset: TilesetResource
    var isDisabled: Boolean
    var isHidden: Boolean

    val colorThemeProperty: Property<ColorTheme>
    val componentStyleSetProperty: Property<ComponentStyleSet>
    val tilesetProperty: Property<TilesetResource>
    val disabledProperty: Property<Boolean>
    val hiddenProperty: Property<Boolean>


    // calculated fields
    val title: String
        get() = this.decorations
            .filterIsInstance<BoxDecorationRenderer>()
            .firstOrNull()?.title ?: ""

    val contentWidth: Int
        get() = contentSize.width

    val contentHeight: Int
        get() = contentSize.height

    var position: Position
        get() = alignment.calculatePosition(size)
        set(value) {
            alignment = positionalAlignment(value)
        }

    /**
     * The final content [Size] of the [Component] that is being built.
     * Content size is calculated this way:
     * - If [preferredContentSize] is set it will be used
     * - If [preferredSize] is set it will be [preferredSize] - [occupiedSize]
     * - If neither one of those are set it defaults to [Size.one]
     */
    val contentSize: Size
        get() = if (preferredSize.isUnknown) {
            if (preferredContentSize.isUnknown) {
                Size.one()
            } else preferredContentSize
        } else preferredSize - decorations.occupiedSize

    /**
     * The final [Size] of the [Component] that is being built.
     * Size is calculated this way:
     * - If [preferredSize] is set it will be used
     * - If [preferredContentSize] is set it will be [preferredContentSize] + [occupiedSize]
     * - If neither one of those are set it defaults to [contentSize] + [occupiedSize]
     */
    val size: Size
        get() = if (preferredSize.isUnknown) {
            if (preferredContentSize.isUnknown) {
                contentSize + decorations.occupiedSize
            } else preferredContentSize + decorations.occupiedSize
        } else preferredSize

    /**
     * Shorthand for `decorations = listOf(decoration)`
     */
    var decoration: ComponentDecorationRenderer?
        get() = decorations.first()
        set(value) {
            value?.let {
                decorations = listOf(value)
            }
        }

    /**
     * Shorthand for [componentRenderer] that uses function syntax.
     */
    @Deprecated("Use lambdas instead (since fun interface upgrade)")
    var renderFunction: (TileGraphics, ComponentRenderContext<T>) -> Unit
        get() = { g, c -> componentRenderer.render(g, c as ComponentRenderContext<Nothing>) }
        set(value) {
            componentRenderer = ComponentRenderer { tileGraphics, context -> value(tileGraphics, context) }
        }

    /**
     * The [Size] that's occupied by [decorations].
     */
    val List<ComponentDecorationRenderer>.occupiedSize
        get() = this.map { it.occupiedSize }.fold(Size.zero(), Size::plus)

    // These functions are necessary for Java users who can't use the
    // Kotlin DSL

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
     * Sets the preferred [Size] of the resulting [Component].
     * The preferred size contains the content and the decorations.
     */
    fun withPreferredSize(size: Size): U {
        preferredSize = size
        return this as U
    }

    /**
     * Sets the preferred content [Size] of the resulting [Component].
     * The preferred content size contains the content only and doesn't contain the decorations.
     */
    fun withPreferredContentSize(size: Size): U {
        preferredContentSize = size
        return this as U
    }

    // DERIVED FUNCTIONS

    fun withPreferredSize(width: Int, height: Int): U = withPreferredSize(Size.create(width, height))

    fun withPreferredContentSize(width: Int, height: Int): U = withPreferredContentSize(Size.create(width, height))

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

}
