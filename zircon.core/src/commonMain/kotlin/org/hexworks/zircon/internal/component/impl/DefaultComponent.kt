package org.hexworks.zircon.internal.component.impl

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
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.ACTIVE
import org.hexworks.zircon.api.component.data.ComponentState.DEFAULT
import org.hexworks.zircon.api.component.data.ComponentState.DISABLED
import org.hexworks.zircon.api.component.data.ComponentState.FOCUSED
import org.hexworks.zircon.api.component.data.ComponentState.HIGHLIGHTED
import org.hexworks.zircon.api.component.extensions.isColorNotUnknown
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

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
    metadata: ComponentMetadata,
    private val renderer: ComponentRenderingStrategy<out Component>,
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

    private val logger = LoggerFactory.getLogger(this::class)

    final override val id: UUID = UUID.randomUUID()
    final override val name: String = metadata.name

    //#region Component hierarchy and state properties
    final override val rootProperty: Property<RootContainer?> = null.toProperty()
    final override var root: RootContainer? by rootProperty.asDelegate()

    final override val parentProperty: Property<InternalContainer?> = null.toProperty()
    final override var parent: InternalContainer? by parentProperty.asDelegate()

    final override val hasParent: ObservableValue<Boolean> = parentProperty.bindTransform { it != null }
    final override val hasFocusProperty = false.toProperty()
    final override val hasFocus: Boolean by hasFocusProperty.asDelegate()
    //#endregion

    //#region Positioning
    final override val originalPosition: Position = metadata.position

    override val positionProperty: ObservableValue<Position> = boundsProperty.bindTransform(Boundable::position)
    override val bounds: Boundable by boundsProperty.asDelegate()

    final override val contentOffset = renderer.contentPosition
    final override val contentSize: Size = renderer.calculateContentSize(size)

    final override val contentBoundsProperty = boundsProperty.bindTransform { (pos) ->
        Boundable.create(pos + contentOffset, contentSize)
    }
    final override val contentBounds: Boundable by contentBoundsProperty.asDelegate()
    //#endregion

    //#region Common properties
    final override val componentStateProperty = DEFAULT.toProperty()
    final override var componentState: ComponentState by componentStateProperty.asDelegate()

    final override val themeProperty = metadata.themeProperty
    final override var theme: ColorTheme by themeProperty.asDelegate()

    final override val componentStyleSetProperty = metadata.componentStyleSetProperty
    final override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    final override val tilesetProperty = metadata.tileset.toProperty(validator = { oldValue, newValue ->
        oldValue isCompatibleWith newValue
    }).apply {
        updateFrom(metadata.tilesetProperty)
    }
    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val hiddenProperty = metadata.hiddenProperty
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    final override val disabledProperty = metadata.disabledProperty
    final override var isDisabled: Boolean by disabledProperty.asDelegate()
    //#endregion

    //#region Misc
    final override val bindingAction = metadata.bindingAction
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

        val updateTheme: (ObservableValueChanged<ColorTheme>) -> Unit = { (_, newTheme) ->
            if (newTheme.isColorNotUnknown) {
                componentStyleSet = convertColorTheme(newTheme)
            }
        }

        val updateStyle: (ObservableValueChanged<ComponentStyleSet>) -> Unit = { (_, newStyle) ->
            if (newStyle.isStyleNotUnknown) {
                componentStyleSet = newStyle
            }
        }

        disabledProperty.onChange(updateState)
        themeProperty.onChange(updateTheme)
        componentStyleSetProperty.onChange(updateStyle)
        componentState = if (isDisabled) DISABLED else DEFAULT

        if (componentStyleSet.isStyleNotUnknown and theme.isColorNotUnknown) {
            componentStyleSet = convertColorTheme(theme)
        }
    }

    //#region Object overrides
    override fun toString(): String {
        val text = if (this is TextOverride) ", text=${textProperty.value}" else ""
        return "${name.ifBlank { this::class.simpleName }}(id=${id.toString().substring(0, 4)}, " +
                "position=$position, size=$size, " +
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

    override fun render(): TileGraphics = FastTileGraphics(
        initialSize = size,
        initialTileset = tileset
    ).also { tg ->
        renderer.asInvariant().render(this@DefaultComponent, tg)
    }

    // TODO: 💡 optimize this by doing a render whenever the component changes
    override fun render(graphics: TileGraphics) = renderer.asInvariant().render(this, graphics)

    /**
     * Moves this component to the given **absolute** position
     */
    //! TODO: Refactor to return result class instead of Boolean
    override fun moveTo(position: Position): Boolean {
        parent?.let { parent ->
            val newBounds = movable.withPosition(position)
            require(parent.containsBoundable(newBounds)) {
                "Can't move Component $this with new bounds ($newBounds) out of its parent's bounds: $parent."
            }
        }
        movable.moveTo(position)
        return true
    }

    //#region Component state and events
    override fun resetState() {
        clearFocus()
        componentState = DEFAULT
        moveTo(originalPosition)
    }

    override fun asInternalComponent(): InternalComponent = this

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

    final override fun clearFocus() = whenConnectedToRoot { root ->
        root.eventBus.publish(
            event = ClearFocus(
                component = this,
                emitter = this
            ),
            eventScope = root.eventScope
        )
    }

    override fun focusGiven() = whenEnabled {
        updateComponentState(FOCUS_GIVEN)
        hasFocusProperty.value = true
    }

    override fun focusTaken() = whenEnabled {
        updateComponentState(FOCUS_TAKEN)
        hasFocusProperty.value = false
    }

    override fun acceptsFocus() = isDisabled.not()

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_ENTERED)
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_EXITED)
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            updateComponentState(MOUSE_RELEASED)
            Processed
        } else Pass
    }

    override fun activated() = whenEnabledRespondWith {
        updateComponentState(ACTIVATED)
        Processed
    }

    override fun deactivated() = whenEnabledRespondWith {
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
    enum class EventType {
        FOCUS_GIVEN, FOCUS_TAKEN, MOUSE_ENTERED, MOUSE_EXITED, MOUSE_PRESSED, MOUSE_RELEASED, ACTIVATED, DEACTIVATED
    }

    data class ComponentStateKey(
        val oldState: ComponentState,
        val isFocused: Boolean,
        val eventType: EventType
    )
    //#endregion

    companion object {

        protected val COMPONENT_STATE_TRANSITIONS = mapOf(
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
