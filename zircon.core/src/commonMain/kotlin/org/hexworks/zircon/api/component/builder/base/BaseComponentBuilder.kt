package org.hexworks.zircon.api.component.builder.base

import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.data.CommonComponentProperties
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer

/**
 * This class can be used as a base class for creating builders for [Component]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
abstract class BaseComponentBuilder<T : Component>(
    initialRenderer: ComponentRenderer<out T>
) : Builder<T>, ComponentEventSource {

    protected var props: CommonComponentProperties<T> = CommonComponentProperties(
        componentRenderer = initialRenderer
    )
        private set

    /**
     * The name of this component. Not mandatory, but if present it will
     * be used for debug purposes.
     */
    var name: String = ""

    /**
     * This [AlignmentStrategy] will be used then the component
     * is aligned within its parent.
     */
    var alignment: AlignmentStrategy
        get() = props.alignmentStrategy
        set(value) {
            props.alignmentStrategy = value
        }

    /**
     * The [Size] allocated for the whole component.
     * **Note that** only one of [preferredSize] and [preferredContentSize]
     * can be set at any given time.
     * Encompasses the [contentSize] and the [occupiedSize].
     */
    open var preferredSize: Size
        get() = props.preferredSize
        set(value) {
            if (preferredContentSize.isNotUnknown) {
                preferredContentSize = Size.unknown()
            }
            props.preferredSize = value
        }

    /**
     * The [Size] allocated for the **contents** of the resulting component.
     * **Note that** only one of [preferredSize] and [preferredContentSize]
     * can be set at any given time.
     * This doesn't contain [occupiedSize].
     */
    open var preferredContentSize: Size
        get() = props.preferredContentSize
        set(value) {
            if (preferredSize.isNotUnknown) {
                preferredSize = Size.unknown()
            }
            props.preferredContentSize = value
        }

    /**
     * Sets whether the component's properties should be updated
     * when it is attached to a parent or not. Use this if you want
     * to apply custom themes or styles to your attached component.
     */
    var updateOnAttach: Boolean
        get() = props.updateOnAttach
        set(value) {
            props.updateOnAttach = value
        }

    /**
     * The renderer which will be used to render the resulting component.
     */
    var componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer
        set(value) {
            props.componentRenderer = value
        }

    // properties
    val colorThemeProperty: Property<ColorTheme>
        get() = props.themeProperty
    var colorTheme: ColorTheme
        get() = props.theme
        set(value) {
            props.theme = value
        }

    val componentStyleSetProperty: Property<ComponentStyleSet>
        get() = props.componentStyleSetProperty
    var componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet
        set(value) {
            props.componentStyleSet = value
        }

    val tilesetProperty: Property<TilesetResource>
        get() = props.tilesetProperty
    var tileset: TilesetResource
        get() = props.tileset
        set(value) {
            props.tileset = value
        }

    val disabledProperty: Property<Boolean>
        get() = props.disabledProperty
    var isDisabled: Boolean
        get() = props.isDisabled
        set(value) {
            props.isDisabled = value
        }

    val hiddenProperty: Property<Boolean>
        get() = props.hiddenProperty
    var isHidden: Boolean
        get() = props.isHidden
        set(value) {
            props.isHidden = value
        }

    // --== calculated fields ==--

    val title: String
        get() = this.decorationRenderers
            .filterIsInstance<BoxDecorationRenderer>()
            .firstOrNull()?.title ?: ""

    val contentWidth: Int
        get() = contentSize.width

    val contentHeight: Int
        get() = contentSize.height

    var position: Position
        get() = alignment.calculatePosition(size)
        set(value) {
            alignment = ComponentAlignments.positionalAlignment(value)
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
        } else preferredSize - decorationRenderers.occupiedSize

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
                contentSize + decorationRenderers.occupiedSize
            } else preferredContentSize + decorationRenderers.occupiedSize
        } else preferredSize

    /**
     * Sets the decorations that should be used for the component.
     * This also forces a recalculation of [contentSize]
     */
    var decorationRenderers: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers
        set(value) {
            props.decorationRenderers = value.reversed()
        }

    /**
     * The [Size] that's occupied by [decorationRenderers].
     */
    val List<ComponentDecorationRenderer>.occupiedSize
        get() = this.map { it.occupiedSize }.fold(Size.zero(), Size::plus)

    /**
     * Aligns the resulting [Component] within the [tileGrid] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(tileGrid, alignment))`
     */
    fun withAlignmentWithin(tileGrid: TileGrid, alignment: ComponentAlignment) {
        this.alignment = ComponentAlignments.alignmentWithin(tileGrid, alignment)
    }

    /**
     * Aligns the resulting [Component] within the [container] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(container, alignment))`
     */
    fun withAlignmentWithin(container: Container, alignment: ComponentAlignment) {
        this.alignment = ComponentAlignments.alignmentWithin(container, alignment)
    }

    /**
     * Aligns the resulting [Component] around the [component] using the
     * given [alignment].
     * Same as calling `withAlignment(alignmentWithin(container, alignment))`
     */
    fun withAlignmentAround(component: Component, alignment: ComponentAlignment) {
        this.alignment = ComponentAlignments.alignmentAround(component, alignment)
    }

    // --== private zone ==--

    private val logger = LoggerFactory.getLogger(this::class)
    private val subscriptionFutures = mutableListOf<SubscriptionFuture>()

    override fun handleComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> UIEventResponse
    ): Subscription = SubscriptionFuture {
        it.handleComponentEvents(eventType, handler)
    }.apply {
        subscriptionFutures.add(this)
    }

    override fun processComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> Unit
    ): Subscription = SubscriptionFuture {
        it.processComponentEvents(eventType, handler)
    }.apply {
        subscriptionFutures.add(this)
    }

    protected fun T.attachListeners(): T {
        subscriptionFutures.forEach {
            it.complete(this)
        }
        return this
    }

    protected fun createRenderingStrategy() = DefaultComponentRenderingStrategy(
        decorationRenderers = decorationRenderers,
        componentRenderer = componentRenderer as ComponentRenderer<T>,
        componentPostProcessors = props.postProcessors
    )

    protected open fun createMetadata(): ComponentMetadata {
        if (updateOnAttach.not()) {
            require(tileset.isNotUnknown) {
                "When not updating on attach a component must have its own tileset."
            }
            require(colorTheme.isNotUnknown || componentStyleSet.isNotUnknown) {
                "When not updating on attach a component must either have its own theme or component style set"
            }
        }
        return ComponentMetadata(
            relativePosition = position,
            size = size,
            name = name,
            updateOnAttach = updateOnAttach,
            themeProperty = colorThemeProperty,
            componentStyleSetProperty = componentStyleSetProperty,
            tilesetProperty = tilesetProperty,
            hiddenProperty = hiddenProperty,
            disabledProperty = disabledProperty
        )
    }

    protected fun fixContentSizeFor(length: Int) {
        if (length doesntFitWithin contentSize) {
            this.preferredContentSize = Size.create(length, 1)
        }
    }

    protected infix fun Size.fitsWithin(other: Size) = other.toRect().containsBoundable(toRect())

    private infix fun Int.doesntFitWithin(size: Size) = this > size.width * size.height

    internal fun withProps(props: CommonComponentProperties<T>) {
        this.props = props
    }

    /**
     * This [Subscription] can be used as an adapter between a [Component]
     * that will be created by this [ComponentBuilder] and the component
     * event listeners created by the user.
     */
    private class SubscriptionFuture(
        private val onComponentCreated: (Component) -> Subscription
    ) : Subscription {

        private var delegate: Subscription? = null
        private var futureDisposeState: DisposeState? = null

        override val disposeState: DisposeState
            get() = delegate?.disposeState ?: NotDisposed

        override fun dispose(disposeState: DisposeState) {
            futureDisposeState = disposeState
            delegate?.dispose(disposeState)
        }

        fun complete(component: Component) {
            delegate = onComponentCreated(component)
        }
    }

}

/**
 * Creates a new [DecorationsBuilder] using the builder DSL and returns it.
 */
fun <T : Component> BaseComponentBuilder<T>.decorations(init: DecorationsBuilder.() -> Unit) {
    decorationRenderers = DecorationsBuilder().apply(init).build()
}


