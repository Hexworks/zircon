package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.extensions.isColorNotUnknown
import org.hexworks.zircon.api.component.extensions.isStyleNotUnknown
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.isSizeNotUnknown
import org.hexworks.zircon.api.data.extensions.isSizeUnknown
import org.hexworks.zircon.api.data.extensions.toBoundable
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.data.CommonComponentProperties
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import kotlin.require

/**
 * This class can be used as a base class for creating builders for [Component]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
@Suppress("UNCHECKED_CAST")
public abstract class BaseComponentBuilder<T : Component>(
    initialRenderer: ComponentRenderer<out T>
) : Builder<T>, ComponentEventSource {

    internal var props: CommonComponentProperties<T> = CommonComponentProperties(
        componentRenderer = initialRenderer
    )
        private set

    /**
     * The name of this component. Not mandatory, but if present it will
     * be used for debug purposes.
     */
    public var name: String = ""

    /**
     * This [AlignmentStrategy] will be used then the component
     * is aligned within its parent.
     */
    public var alignment: AlignmentStrategy
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
    public open var preferredSize: Size
        get() = props.preferredSize
        set(value) {
            if (preferredContentSize.isSizeNotUnknown) {
                preferredContentSize = Size.UNKNOWN
            }
            props.preferredSize = value
        }

    /**
     * The [Size] allocated for the **contents** of the resulting component.
     * **Note that** only one of [preferredSize] and [preferredContentSize]
     * can be set at any given time.
     * This doesn't contain [occupiedSize].
     */
    public open var preferredContentSize: Size
        get() = props.preferredContentSize
        set(value) {
            if (preferredSize.isSizeNotUnknown) {
                preferredSize = Size.UNKNOWN
            }
            props.preferredContentSize = value
        }

    /**
     * Sets whether the component's properties should be updated
     * when it is attached to a parent or not. Use this if you want
     * to apply custom themes or styles to your attached component.
     */
    public var bindingAction: BindingAction
        get() = props.bindingAction
        set(value) {
            props.bindingAction = value
        }

    /**
     * The renderer which will be used to render the resulting component.
     */
    public var componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer
        set(value) {
            props.componentRenderer = value
        }

    // properties
    public val colorThemeProperty: Property<ColorTheme>
        get() = props.themeProperty
    public var colorTheme: ColorTheme
        get() = props.theme
        set(value) {
            props.theme = value
        }

    public val componentStyleSetProperty: Property<ComponentStyleSet>
        get() = props.componentStyleSetProperty
    public var componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet
        set(value) {
            props.componentStyleSet = value
        }

    public val tilesetProperty: Property<TilesetResource>
        get() = props.tilesetProperty
    public var tileset: TilesetResource
        get() = props.tileset
        set(value) {
            props.tileset = value
        }

    public val disabledProperty: Property<Boolean>
        get() = props.disabledProperty
    public var isDisabled: Boolean
        get() = props.isDisabled
        set(value) {
            props.isDisabled = value
        }

    public val hiddenProperty: Property<Boolean>
        get() = props.hiddenProperty
    public var isHidden: Boolean
        get() = props.isHidden
        set(value) {
            props.isHidden = value
        }

    // --== calculated fields ==--

    public val title: String
        get() = this.decorationRenderers
            .filterIsInstance<BoxDecorationRenderer>()
            .firstOrNull()?.title ?: ""

    public val contentWidth: Int
        get() = contentSize.width

    public val contentHeight: Int
        get() = contentSize.height

    public var position: Position
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
    public val contentSize: Size
        get() = if (preferredSize.isSizeUnknown) {
            if (preferredContentSize.isSizeUnknown) {
                Size.ONE
            } else preferredContentSize
        } else preferredSize - decorationRenderers.occupiedSize

    /**
     * The final [Size] of the [Component] that is being built.
     * Size is calculated this way:
     * - If [preferredSize] is set it will be used
     * - If [preferredContentSize] is set it will be [preferredContentSize] + [occupiedSize]
     * - If neither one of those are set it defaults to [contentSize] + [occupiedSize]
     */
    public val size: Size
        get() = if (preferredSize.isSizeUnknown) {
            if (preferredContentSize.isSizeUnknown) {
                contentSize + decorationRenderers.occupiedSize
            } else preferredContentSize + decorationRenderers.occupiedSize
        } else preferredSize

    /**
     * Sets the decorations that should be used for the component.
     * This also forces a recalculation of [contentSize]
     */
    public var decorationRenderers: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers
        set(value) {
            props.decorationRenderers = value.reversed()
        }

    /**
     * The [Size] that's occupied by [decorationRenderers].
     */
    public val List<ComponentDecorationRenderer>.occupiedSize: Size
        get() = this.map { it.occupiedSize }.fold(Size.ZERO, Size::plus)

    /**
     * Aligns the resulting [Component] within the [tileGrid] using the
     * given [alignment]. Shorthand for `alignment = alignmentWithin(tileGrid, alignment)`.
     */
    public fun alignWithin(tileGrid: TileGrid, alignment: ComponentAlignment) {
        this.alignment = ComponentAlignments.alignmentWithin(tileGrid, alignment)
    }

    /**
     * Aligns the resulting [Component] within the [container] using the
     * given [alignment]. Shorthand for `alignment = alignmentWithin(container, alignment)`.
     */
    public fun alignWithin(container: Container, alignment: ComponentAlignment) {
        this.alignment = ComponentAlignments.alignmentWithin(container, alignment)
    }

    /**
     * Aligns the resulting [Component] around the [component] using the
     * given [alignment]. Shorthand for `alignment = alignmentAround(component, alignment)`.
     */
    public fun alignAround(component: Component, alignment: ComponentAlignment) {
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

    protected fun createRenderingStrategy(): ComponentRenderingStrategy<T> = DefaultComponentRenderingStrategy(
        decorationRenderers = decorationRenderers,
        componentRenderer = componentRenderer as ComponentRenderer<T>,
        componentPostProcessors = props.postProcessors
    )

    protected open fun createMetadata(): ComponentMetadata {
        if (bindingAction != UpdateOnBind) {
            require(tileset.isNotUnknown) {
                "When not updating on attach a component must have its own tileset."
            }
            require(colorTheme.isColorNotUnknown || componentStyleSet.isStyleNotUnknown) {
                "When not updating on attach a component must either have its own theme or component style set"
            }
        }
        return ComponentMetadata(
            position = position,
            size = size,
            name = name,
            bindingAction = bindingAction,
            themeProperty = colorThemeProperty,
            componentStyleSetProperty = componentStyleSetProperty,
            tilesetProperty = tilesetProperty,
            hiddenProperty = hiddenProperty,
            disabledProperty = disabledProperty
        )
    }

    protected fun fixContentSizeFor(length: Int) {
        if (length doesntFitWithin contentSize) {
            this.preferredContentSize = Size(length, 1)
        }
    }

    protected infix fun Size.fitsWithin(other: Size): Boolean = other.toBoundable().containsBoundable(toBoundable())

    private infix fun Int.doesntFitWithin(size: Size) = this > size.width * size.height

    internal fun withProps(props: CommonComponentProperties<T>) {
        this.props = props
    }

    /**
     * This [Subscription] can be used as an adapter between a [Component]
     * that will be created by this [BaseComponentBuilder] and the component
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
public fun <T : Component> BaseComponentBuilder<T>.decorations(init: DecorationsBuilder.() -> Unit) {
    decorationRenderers = DecorationsBuilder().apply(init).build()
}

/**
 * Overwrites the current list of [ComponentDecorationRenderer]s with the given [decoration].
 */
public var BaseComponentBuilder<out Component>.decoration: ComponentDecorationRenderer?
    get() = null
    set(value) {
        value?.let {
            decorationRenderers = listOf(value)
        }
    }




public fun <T : Component> BaseComponentBuilder<T>.withComponentRenderer(init: ComponentRenderer<T>) {
    apply {
        componentRenderer = init
    }
}

