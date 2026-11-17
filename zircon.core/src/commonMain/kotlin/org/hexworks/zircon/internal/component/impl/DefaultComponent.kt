package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.behavior.extensions.withPosition
import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.ACTIVE
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.DISABLED
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.data.ComponentState.HIGHLIGHTED
import org.hexworks.zircon.api.component.extensions.isStyleNotUnknown
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.extensions.asInvariant
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.extensions.whenConnectedToRoot
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.ACTIVATED
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.DEACTIVATED
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.FOCUS_GIVEN
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.FOCUS_TAKEN
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.MOUSE_ENTERED
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.MOUSE_EXITED
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.MOUSE_RELEASED
import org.hexworks.zircon.internal.event.ZirconEvent.ClearFocus
import org.hexworks.zircon.internal.event.ZirconEvent.RequestFocusFor
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.internal.uievent.impl.DefaultUIEventProcessor
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.theme.PinnedAspects
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.theme.ComponentPalette

@Suppress("UNCHECKED_CAST")
public abstract class DefaultComponent(
    metadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<out Component>,
    private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault(),
    override val boundsProperty: Property<Boundable> = Boundable.create(
        position = metadata.position,
        size = metadata.size
    ).toProperty(),
    private val movable: Movable = DefaultMovable(boundsProperty),
) : InternalComponent,
    ComponentEventSource by uiEventProcessor,
    Movable by movable,
    UIEventProcessor by uiEventProcessor {

    override val absolutePosition: Position
        get() = position

    public val relativePosition: Position
        get() = parent?.let { position - it.contentBounds.position } ?: position

    private val logger = LoggerFactory.getLogger(this::class)

    final override val id: UUID = UUID.randomUUID()
    final override val name: String = metadata.name
    final override val role: ComponentRole = metadata.role
    final override val variant: Variant = metadata.variant
    final override val part: Part = metadata.part
    final override val orientation: Orientation = metadata.orientation
    private val kind: ComponentRole = metadata.kind

    //#region Rendering strategy
    /**
     * The strategy in effect: what the builder made for its [ComponentTheme]; a theme switch
     * replaces it and the content area follows it.
     */
    private var renderingStrategy: ComponentRenderingStrategy<out Component> = renderer

    private val pinnedAspects: PinnedAspects = metadata.pinnedAspects
    private var componentThemeExplicit: Boolean = metadata.componentThemeExplicit
    private var componentPaletteExplicit: Boolean = metadata.componentPaletteExplicit
    //#endregion

    //#region Component hierarchy and state properties
    final override val rootProperty: Property<RootContainer?> = null.toProperty()
    final override var root: RootContainer? by rootProperty.asDelegate()

    final override val parentProperty: Property<InternalContainer?> = null.toProperty()
    final override var parent: InternalContainer? by parentProperty.asDelegate()

    final override val hasParent: ObservableValue<Boolean> = parentProperty.bindTransform { it != null }
    final override val hasFocusProperty: Property<Boolean> = false.toProperty()
    final override val hasFocus: Boolean by hasFocusProperty.asDelegate()
    //#endregion

    //#region Positioning
    final override val originalPosition: Position = metadata.position

    override val positionProperty: ObservableValue<Position> = boundsProperty.bindTransform(Boundable::position)
    override val bounds: Boundable by boundsProperty.asDelegate()

    final override val contentOffset: Position
        get() = renderingStrategy.contentPosition

    // Clamped: a theme whose decorations do not fit leaves no content area rather than a negative one.
    final override val contentSize: Size
        get() = renderingStrategy.calculateContentSize(size).let { (w, h) -> Size(maxOf(0, w), maxOf(0, h)) }

    private val contentBoundsInternal: Property<Boundable> =
        Boundable.create(position + contentOffset, contentSize).toProperty()
    final override val contentBoundsProperty: ObservableValue<Boundable>
        get() = contentBoundsInternal
    final override val contentBounds: Boundable
        get() = contentBoundsInternal.value

    private fun refreshContentBounds() {
        contentBoundsInternal.value = Boundable.create(position + contentOffset, contentSize)
    }
    //#endregion

    //#region Common properties
    final override val componentStateProperty: Property<ComponentState> = DEFAULT.toProperty()
    final override var componentState: ComponentState by componentStateProperty.asDelegate()

    final override val componentPaletteProperty: Property<ComponentPalette> = metadata.componentPaletteProperty
    final override var componentPalette: ComponentPalette
        get() = componentPaletteProperty.value
        set(value) {
            componentPaletteExplicit = true
            componentPaletteProperty.value = value
        }

    final override val componentThemeProperty: Property<ComponentTheme> = metadata.componentThemeProperty
    final override var componentTheme: ComponentTheme
        get() = componentThemeProperty.value
        set(value) {
            componentThemeExplicit = true
            componentThemeProperty.value = value
        }

    final override val componentStyleSetProperty: Property<ComponentStyleSet> = metadata.componentStyleSetProperty
    final override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    final override val tilesetProperty: Property<TilesetResource> = metadata.tileset.toProperty(validator = { oldValue, newValue ->
        oldValue isCompatibleWith newValue
    }).apply {
        updateFrom(metadata.tilesetProperty)
    }
    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val hiddenProperty: Property<Boolean> = metadata.hiddenProperty
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    final override val disabledProperty: Property<Boolean> = metadata.disabledProperty

    override val activationShortcut: KeyboardEventMatcher? = metadata.activationShortcut
    final override var isDisabled: Boolean by disabledProperty.asDelegate()
    //#endregion

    //#region Misc
    final override val bindingAction: BindingAction = metadata.bindingAction
    override val children: ObservableList<out InternalComponent> = persistentListOf<InternalComponent>().toProperty()
    //#endregion

    init {
        val updateState: (ObservableValueChanged<Boolean>) -> Unit = { (_, isDisabled) ->
            componentState = if (isDisabled) {
                logger.debug { "Component disabled. Applying disabled style." }
                DISABLED
            } else {
                logger.debug { "Component enabled. Applying enabled style." }
                DEFAULT
            }
        }

        val updateStyle: (ObservableValueChanged<ComponentStyleSet>) -> Unit = { (_, newStyle) ->
            if (newStyle.isStyleNotUnknown) {
                componentStyleSet = newStyle
            }
        }

        disabledProperty.onChange(updateState)
        componentStyleSetProperty.onChange(updateStyle)
        boundsProperty.onChange { refreshContentBounds() }
        componentThemeProperty.onChange { (_, newTheme) -> applyComponentTheme(newTheme, initial = false) }
        componentPaletteProperty.onChange { applyPalette() }
        componentState = if (isDisabled) DISABLED else DEFAULT

        applyComponentTheme(componentTheme, initial = true)
    }

    //#region Component theme
    final override fun inheritComponentTheme(theme: ComponentTheme) {
        if (componentThemeExplicit) return
        componentThemeProperty.value = theme
    }

    final override fun inheritComponentPalette(palette: ComponentPalette) {
        if (componentPaletteExplicit) return
        componentPaletteProperty.value = palette
    }

    /**
     * Recolors the component from the current theme's derivation and the current palette,
     * unless its style set is pinned.
     */
    private fun applyPalette() {
        if (pinnedAspects.componentStyleSet) return
        componentTheme.resolveEntry(role, variant, part, kind)
            ?.stylesFor(componentPalette, role, variant, part)
            ?.let { componentStyleSet = it }
        invalidateRender()
    }

    /**
     * Replaces the rendering strategy and the style set with what [theme] resolves for this
     * component (its own entries, then the default theme's), keeping the pinned aspects; the
     * colors are the entry's derivation from the current palette. Decorations that do not fit
     * into the component (no content cell would be left) are skipped; the renderer and the
     * colors still apply. [initial] is `true` from the constructor, when there are no children
     * to move yet.
     */
    private fun applyComponentTheme(theme: ComponentTheme, initial: Boolean) {
        val oldOffset = contentOffset
        val entry = theme.resolveEntry(role, variant, part, kind)
        if (entry != null) {
            val current = renderingStrategy as? DefaultComponentRenderingStrategy<Component>
            val renderer = if (pinnedAspects.componentRenderer) null else entry.componentRenderer
            val decorations = if (pinnedAspects.decorationRenderers) null else entry.decorationRenderers?.takeIf { it.fits() }
            val postProcessors = entry.componentPostProcessors
            if (renderer != null || decorations != null || postProcessors != null) {
                val contentRenderer = (renderer ?: current?.componentRenderer) as? ComponentRenderer<Component>
                if (contentRenderer != null) {
                    renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = contentRenderer,
                        decorationRenderers = decorations ?: current?.decorationRenderers ?: emptyList(),
                        componentPostProcessors = (postProcessors ?: current?.componentPostProcessors ?: emptyList())
                                as List<ComponentPostProcessor<Component>>,
                    )
                }
            }
        }
        if (!pinnedAspects.componentStyleSet) {
            entry?.stylesFor(componentPalette, role, variant, part)?.let { componentStyleSet = it }
        }
        refreshContentBounds()
        if (!initial) {
            val delta = contentOffset - oldOffset
            if (delta != Position.ZERO) onContentOffsetChanged(delta)
        }
        invalidateRender()
    }

    private fun List<ComponentDecorationRenderer>.fits(): Boolean {
        val occupied = fold(Size.ZERO) { acc, decoration -> acc + decoration.occupiedSize }
        val own = this@DefaultComponent.size
        return own.width > occupied.width && own.height > occupied.height
    }

    /**
     * Called when a theme change moved the content area by [delta]; containers move their
     * children along so they stay inside it.
     */
    protected open fun onContentOffsetChanged(delta: Position) {}
    //#endregion

    //#region Object overrides
    override fun toString(): String {
        val text = if (this is TextOverride) ", text=${textProperty.value}" else ""
        return "${name.ifBlank { this::class.simpleName }}(id=${id.toString().substring(0, 4)}, " +
                "absolutePosition=$absolutePosition, relativePosition=$relativePosition, size=$size, " +
                "state=$componentState, disabled=$isDisabled$text)"
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (this::class != other::class) return false
        if (other !is DefaultComponent) return false
        return id == other.id
    }

    final override fun hashCode(): Int {
        return id.hashCode()
    }
    //#endregion

    //#region Rendering
    private var renderedGraphics: FastTileGraphics? = null
    private var renderIsStale = true
    private var renderHooksInstalled = false

    /**
     * Observables beyond the common ones (state, style, theme, tileset, text, selection,
     * scrolling, focus) whose change means this component has to be redrawn.
     */
    protected open val renderInputs: List<ObservableValue<*>>
        get() = emptyList()

    final override fun invalidateRender() {
        renderIsStale = true
    }

    /**
     * Renders this component once and keeps the result until [invalidateRender] is called,
     * which happens from the hooks installed by [installRenderHooks] and from the UI event
     * dispatcher. The renderer reads this every frame, so the cache is what keeps a static
     * screen cheap.
     */
    override fun render(): TileGraphics {
        if (!renderHooksInstalled) {
            installRenderHooks()
            renderHooksInstalled = true
        }
        val cached = renderedGraphics?.takeIf { it.size == size && it.tileset == tileset }
        if (cached != null && !renderIsStale && !renderingStrategy.rendersOnEveryFrame) {
            return cached
        }
        val target = cached?.apply { clear() } ?: FastTileGraphics(initialSize = size, initialTileset = tileset)
        renderingStrategy.asInvariant().render(this, target)
        renderedGraphics = target
        renderIsStale = false
        return target
    }

    override fun render(graphics: TileGraphics): Unit = renderingStrategy.asInvariant().render(this, graphics)

    // Runs on the first render, when subclasses are fully constructed, so their
    // delegated properties (text, selection, scrolling) are safe to touch.
    private fun installRenderHooks() {
        val inputs = mutableListOf<ObservableValue<*>>(
            componentStateProperty, componentStyleSetProperty, tilesetProperty,
            hiddenProperty, disabledProperty, hasFocusProperty, boundsProperty,
        )
        if (this is TextOverride) inputs += textProperty
        if (this is TitleOverride) inputs += titleProperty
        if (this is Selectable) inputs += selectedProperty
        if (this is Scrollable) inputs += listOf(visibleOffsetProperty, actualSizeProperty)
        inputs += renderInputs
        inputs.forEach { input -> input.onChange { invalidateRender() } }
    }
    //#endregion

    /**
     * Moves this component to the given **absolute** position.
     * @return `false`, and stays put, if the new bounds would leave the parent
     */
    override fun moveTo(position: Position): Boolean {
        parent?.let { parent ->
            val newBounds = movable.withPosition(position)
            if (!parent.containsBoundable(newBounds)) {
                logger.debug { "Not moving $this to $position: bounds $newBounds leave the parent $parent." }
                return false
            }
        }
        movable.moveTo(position)
        return true
    }

    override fun shiftBy(delta: Position) {
        if (delta != Position.ZERO) movable.moveTo(position + delta)
    }

    //#region Component state and events
    override fun resetState() {
        clearFocus()
        componentState = DEFAULT
        moveTo(originalPosition)
    }


    override fun clearCustomStyle() {
        componentStyleSet = ComponentStyleSet.DEFAULT_STYLE
    }

    final override fun requestFocus(): Boolean {
        whenConnectedToRoot { root ->
            root.eventBus.publish(
                event = RequestFocusFor(
                    component = this,
                    emitter = this
                ),
                eventScope = root.eventScope
            )
        }
        return hasFocusProperty.value
    }

    final override fun clearFocus(): Unit = whenConnectedToRoot { root ->
        root.eventBus.publish(
            event = ClearFocus(
                component = this,
                emitter = this
            ),
            eventScope = root.eventScope
        )
    }

    override fun focusGiven(): UIEventResponse = whenEnabled {
        updateComponentState(FOCUS_GIVEN)
        hasFocusProperty.value = true
    }

    override fun focusTaken(): UIEventResponse = whenEnabled {
        updateComponentState(FOCUS_TAKEN)
        hasFocusProperty.value = false
    }

    override fun acceptsFocus(): Boolean = isDisabled.not()

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_ENTERED)
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_EXITED)
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_RELEASED)
            Processed
        } else Pass
    }

    override fun activated(): UIEventResponse = whenEnabledRespondWith {
        updateComponentState(ACTIVATED)
        Processed
    }

    override fun deactivated(): UIEventResponse = whenEnabledRespondWith {
        updateComponentState(DEACTIVATED)
        Processed
    }


    private fun updateComponentState(eventType: EventType) {
        val key = ComponentStateKey(
            oldState = componentState,
            isFocused = hasFocusProperty.value,
            eventType = eventType
        )
        logger.debug {
            "Updating component state with $key."
        }
        COMPONENT_STATE_TRANSITIONS[key]?.let {
            logger.debug {
                "Component state was updated to state $it."
            }
            componentState = it
        } ?: run {
            logger.debug {
                "There was no corresponding key, no update happened."
            }
        }
    }
    //#endregion

    //#region Inner classes
    public enum class EventType {
        FOCUS_GIVEN, FOCUS_TAKEN, MOUSE_ENTERED, MOUSE_EXITED, MOUSE_PRESSED, MOUSE_RELEASED, ACTIVATED, DEACTIVATED
    }

    public data class ComponentStateKey(
        val oldState: ComponentState,
        val isFocused: Boolean,
        val eventType: EventType
    )
    //#endregion

    public companion object {

        protected val COMPONENT_STATE_TRANSITIONS: Map<ComponentStateKey, ComponentState> = mapOf(
            ComponentStateKey(DEFAULT, false, FOCUS_GIVEN) to FOCUSED,
            ComponentStateKey(DEFAULT, false, MOUSE_ENTERED) to HIGHLIGHTED,
            ComponentStateKey(HIGHLIGHTED, true, MOUSE_EXITED) to FOCUSED,
            ComponentStateKey(HIGHLIGHTED, true, ACTIVATED) to ACTIVE,
            ComponentStateKey(HIGHLIGHTED, false, MOUSE_EXITED) to DEFAULT,
            ComponentStateKey(HIGHLIGHTED, false, ACTIVATED) to ACTIVE,
            ComponentStateKey(ACTIVE, true, MOUSE_EXITED) to FOCUSED,
            ComponentStateKey(ACTIVE, true, MOUSE_RELEASED) to HIGHLIGHTED,
            ComponentStateKey(ACTIVE, true, DEACTIVATED) to FOCUSED,
            ComponentStateKey(FOCUSED, true, FOCUS_TAKEN) to DEFAULT,
            ComponentStateKey(FOCUSED, true, MOUSE_ENTERED) to HIGHLIGHTED,
            ComponentStateKey(FOCUSED, true, MOUSE_RELEASED) to HIGHLIGHTED,
            ComponentStateKey(FOCUSED, true, ACTIVATED) to ACTIVE,

            // UNINTUITIVE SPECIAL CASES

            // this particular case can happen when the user is pressing a button which
            // on its action callback removes the focus from it
            ComponentStateKey(ACTIVE, false, DEACTIVATED) to HIGHLIGHTED,
            // This happens when space is pressed on a component, then the user presses tab (and focus is lost)
            ComponentStateKey(ACTIVE, true, FOCUS_TAKEN) to DEFAULT,
            // this happens when a component is removed when clicked in an HBox (or something similar)
            // and the next (to its right) component gets realigned
            ComponentStateKey(DEFAULT, false, MOUSE_RELEASED) to HIGHLIGHTED,
            // these happen when a modal window is opened when a button is clicked
            ComponentStateKey(HIGHLIGHTED, true, FOCUS_TAKEN) to DEFAULT,
            ComponentStateKey(HIGHLIGHTED, false, DEACTIVATED) to DEFAULT,
        )
    }
}
