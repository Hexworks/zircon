package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
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
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import kotlin.require
import org.hexworks.zircon.api.component.theme.ThemeEntry
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.theme.ComponentPalette

/**
 * This class can be used as a base class for creating builders for [Component]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 *
 * A builder starts from the stock look of [initialRole], the default theme's renderer and
 * decorations for it, and applies the [ComponentTheme] in effect when it builds
 * ([ZirconDefaults.componentTheme][org.hexworks.zircon.api.config.ZirconDefaults.componentTheme],
 * or the theme of the factory or parent builder it is built through). [initialRenderer] is for
 * a kind no theme knows ([ComponentRole.NONE], a custom component).
 */
@Suppress("UNCHECKED_CAST")
public abstract class BaseComponentBuilder<T : Component>(
    initialRenderer: ComponentRenderer<out T> = NoOpComponentRenderer(),
    initialRole: ComponentRole = ComponentRole.NONE,
) : Builder<T>, ComponentEventSource {

    internal var props: CommonComponentProperties<T> = CommonComponentProperties(
        componentRenderer = initialRenderer,
        kind = initialRole,
        role = initialRole,
    )
        private set

    init {
        // The stock look is the builder's default: one source for what a kind looks like.
        ComponentThemes.default().resolveEntry(initialRole)?.let { entry ->
            entry.componentRenderer?.let { props.componentRenderer = it as ComponentRenderer<out T> }
            entry.decorationRenderers?.let { props.decorationRenderers = it }
        }
    }

    /**
     * The component kind this builder makes (`LABEL`), the last fallback of a theme lookup.
     */
    internal val kind: ComponentRole
        get() = props.kind

    /**
     * What the resulting component is for a [ComponentTheme]. Defaults to the component kind;
     * a fragment sets it to the composite (`CARD`) and says which [part] the component is.
     */
    public var role: ComponentRole
        get() = props.role
        set(value) {
            props.role = value
        }

    /**
     * The semantic weight within [role] (`PRIMARY`, `WARNING`); a theme may give each variant
     * its own look, and a palette its own colors.
     */
    public var variant: Variant
        get() = props.variant
        set(value) {
            props.variant = value
        }

    /**
     * Which piece of a composite the component is (`TITLE`); [Part.NONE] for a standalone one.
     */
    public var part: Part
        get() = props.part
        set(value) {
            props.part = value
        }

    /**
     * Which way the component runs; theme renderers read it.
     */
    public var orientation: Orientation
        get() = props.orientation
        set(value) {
            props.orientation = value
        }

    /**
     * The [ComponentTheme] of the resulting component. Setting it here pins it: the parent's
     * theme does not replace it on attach or when it changes, so a themed subtree can live in
     * a screen with another theme. Not setting it leaves the component following its parent.
     */
    public var componentTheme: ComponentTheme
        get() = props.componentTheme
        set(value) {
            props.componentThemeExplicit = true
            props.componentTheme = value
            applyThemeEntry(value)
        }

    /**
     * Gives the builder [theme] without pinning it or applying its entry yet (that happens in
     * [createMetadata], once the block has settled the role): a factory or a parent builder
     * passes its theme on this way, so children built inside the block pick it up too.
     */
    internal fun presetTheme(theme: ComponentTheme) {
        props.componentTheme = theme
    }
    public val componentThemeProperty: Property<ComponentTheme>
        get() = props.componentThemeProperty

    /**
     * The [ComponentPalette] of the resulting component. Setting it here pins it: the parent's
     * palette does not replace it on attach or when it changes. Not setting it leaves the
     * component following its parent.
     */
    public var componentPalette: ComponentPalette
        get() = props.componentPalette
        set(value) {
            props.componentPaletteExplicit = true
            props.componentPalette = value
            applyThemeEntry(props.componentTheme)
        }

    /**
     * Gives the builder [palette] without pinning it, the way a parent builder passes its
     * palette on.
     */
    internal fun presetPalette(palette: ComponentPalette) {
        props.componentPalette = palette
        applyThemeEntry(props.componentTheme)
    }
    public val componentPaletteProperty: Property<ComponentPalette>
        get() = props.componentPaletteProperty

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
            props.pinnedAspects = props.pinnedAspects.copy(componentRenderer = true)
        }

    // properties
    public val componentStyleSetProperty: Property<ComponentStyleSet>
        get() = props.componentStyleSetProperty
    public var componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet
        set(value) {
            props.componentStyleSet = value
            props.pinnedAspects = props.pinnedAspects.copy(componentStyleSet = true)
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
            props.pinnedAspects = props.pinnedAspects.copy(decorationRenderers = true)
        }

    /**
     * A default set by a fragment (not the user) over what the theme gave the builder: it
     * does not pin the aspect, so a theme that says otherwise still wins.
     */
    internal fun defaultDecorationRenderers(value: List<ComponentDecorationRenderer>) {
        props.decorationRenderers = value.reversed()
    }

    internal fun defaultComponentRenderer(value: ComponentRenderer<out T>) {
        props.componentRenderer = value
    }

    internal fun defaultComponentStyleSet(value: ComponentStyleSet) {
        props.componentStyleSet = value
    }

    /**
     * Pre-configures this builder from [theme]'s entry for [role] (if any): the renderer,
     * the decorations and the style set that are not pinned. The component starts with
     * [theme] as its component theme, not pinned, so a themed parent can still replace it.
     */
    internal fun applyThemeEntry(theme: ComponentTheme) {
        props.componentTheme = theme
        val entry: ThemeEntry = theme.resolveEntry(role, variant, part, kind) ?: return
        val pins = props.pinnedAspects
        if (!pins.componentRenderer) entry.componentRenderer?.let { props.componentRenderer = it as ComponentRenderer<out T> }
        if (!pins.decorationRenderers) entry.decorationRenderers?.let { props.decorationRenderers = it }
        if (!pins.componentStyleSet) entry.stylesFor(props.componentPalette, role, variant, part)?.let { props.componentStyleSet = it }
        entry.componentPostProcessors?.let { props.postProcessors = it as List<ComponentPostProcessor<T>> }
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

    /**
     * A keyboard shortcut that activates the resulting component from anywhere on its
     * screen, for example `KeyboardEventMatcher(type = KEY_PRESSED, code = KeyCode.F1)`.
     */
    public var activationShortcut: KeyboardEventMatcher? = null

    protected open fun createMetadata(): ComponentMetadata {
        // The block has run: the role is final and the user's choices are pinned.
        applyThemeEntry(props.componentTheme)
        if (bindingAction != UpdateOnBind) {
            require(tileset.isNotUnknown) {
                "When not updating on attach a component must have its own tileset."
            }
        }
        return ComponentMetadata(
            position = position,
            size = size,
            name = name,
            bindingAction = bindingAction,
            componentStyleSetProperty = componentStyleSetProperty,
            tilesetProperty = tilesetProperty,
            activationShortcut = activationShortcut,
            hiddenProperty = hiddenProperty,
            disabledProperty = disabledProperty,
            kind = kind,
            role = role,
            variant = variant,
            part = part,
            orientation = orientation,
            componentThemeProperty = componentThemeProperty,
            pinnedAspects = props.pinnedAspects,
            componentThemeExplicit = props.componentThemeExplicit,
            componentPaletteProperty = componentPaletteProperty,
            componentPaletteExplicit = props.componentPaletteExplicit,
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

