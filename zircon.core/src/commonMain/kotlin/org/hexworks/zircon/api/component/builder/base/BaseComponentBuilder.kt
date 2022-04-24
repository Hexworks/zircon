package org.hexworks.zircon.api.component.builder.base

import org.hexworks.cobalt.core.behavior.DisposeState
import org.hexworks.cobalt.core.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.data.CommonComponentProperties
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.jvm.JvmSynthetic

/**
 * This class can be used as a base class for creating [ComponentBuilder]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>(
    initialRenderer: ComponentRenderer<out T>
) : ComponentBuilder<T, U> {

    override var name: String = ""
    protected var props: CommonComponentProperties<T> = CommonComponentProperties(
        componentRenderer = initialRenderer
    )
        private set

    override var alignment: AlignmentStrategy
        get() = props.alignmentStrategy
        set(value) {
            props.alignmentStrategy = value
        }

    override var preferredSize: Size
        get() = props.preferredSize
        set(value) {
            if (preferredContentSize.isNotUnknown) {
                preferredContentSize = Size.unknown()
            }
            props.preferredSize = value
        }

    override var preferredContentSize: Size
        get() = props.preferredContentSize
        set(value) {
            if (preferredSize.isNotUnknown) {
                preferredSize = Size.unknown()
            }
            props.preferredContentSize = value
        }

    override var decorations: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers
        set(value) {
            props.decorationRenderers = value.reversed()
        }

    override var updateOnAttach: Boolean
        get() = props.updateOnAttach
        set(value) {
            props.updateOnAttach = value
        }

    override var componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer
        set(value) {
            props.componentRenderer = value
        }

    // properties
    override val colorThemeProperty: Property<ColorTheme>
        get() = props.themeProperty
    override var colorTheme: ColorTheme
        get() = props.theme
        set(value) {
            props.theme = value
        }

    override val componentStyleSetProperty: Property<ComponentStyleSet>
        get() = props.componentStyleSetProperty
    override var componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet
        set(value) {
            props.componentStyleSet = value
        }

    override val tilesetProperty: Property<TilesetResource>
        get() = props.tilesetProperty
    override var tileset: TilesetResource
        get() = props.tileset
        set(value) {
            props.tileset = value
        }

    override val disabledProperty: Property<Boolean>
        get() = props.disabledProperty
    override var isDisabled: Boolean
        get() = props.isDisabled
        set(value) {
            props.isDisabled = value
        }

    override val hiddenProperty: Property<Boolean>
        get() = props.hiddenProperty
    override var isHidden: Boolean
        get() = props.isHidden
        set(value) {
            props.isHidden = value
        }

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
        decorationRenderers = decorations,
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

    @JvmSynthetic
    internal fun withProps(props: CommonComponentProperties<T>): U {
        this.props = props
        return this as U
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
