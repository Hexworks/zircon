package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Focusable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.internal.component.InternalComponent

/**
 * A [Component] is a graphical text GUI element and can be used either to
 * display information to the user or to enable the user to interact with the program.
 *
 * Components are organized into a tree structure of [Component] elements nested in each other.
 * The component hierarchy **always** has a [Container] at its root. A child [Component]
 * is **always** bounded by its parent. Containers are branches in this tree while [Component]s
 * are leaves. So for example, a panel which is intended to be able to hold other components
 * like a label or a checkbox is a [Container] while a label which is only intended to
 * display information is just a [Component].
 *
 * [Component]s also support UI event handling through [UIEventSource] and [ComponentEventSource]
 * and can handle focus.
 *
 * The [Component] abstraction implements the **Composite** design pattern with [Component]
 * and [Container].
 *
 * Visualization:
 *
 * ### Box Model
 *
 * A [Component] uses a box model where [ComponentDecorationRenderer][org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer]s
 * wrap the content area. The [ComponentRenderer][org.hexworks.zircon.api.component.renderer.ComponentRenderer]
 * draws inside the content area.
 *
 * ```
 * absolutePosition (position on the grid)
 * |
 * v
 * ╔═══╡ Box Decoration ╞═════════╗
 * ║■<── Content offset           ║░
 * ║↑                             ║░
 * ║|                             ║░
 * ║|                             ║░
 * ║|                             ║░
 * ║├─ content height             ║░
 * ║|                             ║░
 * ║|                             ║░
 * ║↓                             ║░
 * ║■ <─ content size (width) ─> ■║░
 * ╚══════════════════════════════╝░
 *  ░░░░░░░░Shadow decoration░░░░░░░
 * ■ <─────  size (width)   ─────> ■
 * ```
 *
 * - [contentOffset] = sum of all `ComponentDecorationRenderer.offset` values
 * - [contentSize] = [size][org.hexworks.zircon.api.behavior.HasSize.size] - sum of all `ComponentDecorationRenderer.occupiedSize` values
 *
 * ### Rendering Pipeline
 *
 * (via [ComponentRenderingStrategy][org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy])
 *
 * 1. A `DrawWindow` is created over the content area and the `ComponentRenderer` renders content into it
 * 2. `ComponentDecorationRenderer`s render their layers (outermost first, each getting a progressively smaller `DrawWindow`)
 * 3. `ComponentPostProcessor`s post-process the content area
 *
 * ### Parent-Child Positioning
 *
 * Children are positioned relative to the parent's content area:
 *
 * ```
 * Parent (absolutePosition = (3, 2))
 * +----------------------------------------------+
 * |   +== parent decoration ===================+ |
 * |   H                                        H |
 * |   H   parent content area (contentSize)    H |
 * |   H                                        H |
 * |   H   child.relativePosition = (2, 1)      H |
 * |   H   |                                    H |
 * |   H   v                                    H |
 * |   H   +--------------------+               H |
 * |   H   |  child decoration  |               H |
 * |   H   |  +--------------+  |               H |
 * |   H   |  | child        |  |               H |
 * |   H   |  | content area |  |               H |
 * |   H   |  +--------------+  |               H |
 * |   H   +--------------------+               H |
 * |   H                                        H |
 * |   +=========================================+ |
 * +----------------------------------------------+
 * ```
 *
 * ```
 * child.absolutePosition = parent.absolutePosition
 *                        + parent.contentOffset
 *                        + child.relativePosition
 *
 * Example: (3,2) + (1,1) + (2,1) = (6, 4)
 * ```
 *
 * [relativeBounds] = `Rect(relativePosition, size)` (bounds of this component relative to its parent)
 *
 * @see Container
 * @see ComponentContainer
 * @see UIEventSource
 * @see ComponentEventSource
 * @see Focusable
 */
//! TODO: add minimum size
interface Component : ComponentEventSource, ComponentProperties, Focusable, Movable, UIEventSource {

    /**
     * The **absolute** position of this [Component], e.g.: the [Position] relative to the
     * top left corner of the grid it is displayed on. This value is context-dependent,
     * and it is calculated based on the parent it is attached to.
     */
    override val position: Position
    val positionProperty: ObservableValue<Position>

    /**
     * The relative position is the position of the top left corner of this [Component]
     * relative to the [contentOffset] of its parent.
     */
    val relativePosition: Position
    val relativePositionProperty: ObservableValue<Position>

    /**
     * The bounds of this [Component] relative to the top left corner of the grid it is
     * displayed on.
     */
    val absoluteBounds: Boundable
    val absoluteBoundsProperty: ObservableValue<Boundable>

    /**
     * The bounds of this [Component] relative to its parent.
     */
    val relativeBounds: Boundable
    val relativeBoundsProperty: ObservableValue<Boundable>

    /**
     * The position of the top left corner of the **content area** (where the component
     * is rendered without the decorations) relative to the top left corner of this
     * [Component]. In other words, the content offset is the sum of the offset positions
     * for each decoration.
     */
    val contentOffset: Position

    /**
     * The [Size] of the content of this [Component]. In other words, the content
     * size is the total size of the component minus the size of its decorations.
     */
    val contentSize: Size

    /**
     * The current style based on [componentStyleSet] according to the current [componentState].
     */
    val currentStyle: StyleSet
        get() = componentStyleSet.fetchStyleFor(componentState)

    val componentState: ComponentState
    val componentStateValue: ObservableValue<ComponentState>

    /**
     * The [ComponentStyleSet] of this [Component]. Note that if you set
     * it by hand it will take precedence over the [ComponentStyleSet] provided
     * by [theme].
     */
    var componentStyleSet: ComponentStyleSet
    val componentStyleSetProperty: Property<out ComponentStyleSet>

    /**
     * Returns this [Component] as an [InternalComponent] which represents
     * the internal API of [Component].
     */
    fun asInternalComponent(): InternalComponent

    /**
     * Clears any custom [componentStyleSet] (if present).
     */
    fun clearCustomStyle()

    /**
     * Resets this component to its original state as it was initially created. This means
     * - Its focus is cleared
     * - Its state is reset to [ComponentState.DEFAULT]
     * - It is moved back to its original position
     *
     * @see AttachedComponent.detach
     */
    fun resetState()

    companion object
}
