package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.impl.DefaultComponent.EventType.*
import org.hexworks.zircon.internal.event.ZirconEvent.ClearFocus
import org.hexworks.zircon.internal.event.ZirconEvent.RequestFocusFor
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.internal.uievent.impl.DefaultUIEventProcessor
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
    metadata: ComponentMetadata,
    private val renderer: ComponentRenderingStrategy<out Component>,
    private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault(),
    private val movable: Movable = DefaultMovable(
        position = metadata.relativePosition,
        size = metadata.size
    )
) : InternalComponent,
    ComponentEventSource by uiEventProcessor,
    Movable by movable,
    UIEventProcessor by uiEventProcessor {

    private val logger = LoggerFactory.getLogger(this::class)

    final override val id: UUID = UUID.randomUUID()
    final override val name: String = metadata.name

    // component state and position
    final override val rootValue = Maybe.empty<RootContainer>().toProperty()
    final override var root: Maybe<RootContainer> by rootValue.asDelegate()
    final override val parentProperty = Maybe.empty<InternalContainer>().toProperty()
    final override var parent: Maybe<InternalContainer> by parentProperty.asDelegate()
    final override val hasParent: ObservableValue<Boolean> = parentProperty.bindTransform { it.isPresent }
    final override val hasFocusValue = false.toProperty()
    final override val hasFocus: Boolean by hasFocusValue.asDelegate()
    final override val absolutePosition: Position
        get() = position
    final override var relativePosition: Position = metadata.relativePosition
        private set
    final override val relativeBounds: Rect
        get() = rect.withPosition(relativePosition)
    final override val contentOffset: Position by lazy { renderer.contentPosition }
    final override val contentSize: Size by lazy { renderer.calculateContentSize(size) }
    final override val componentStateValue = DEFAULT.toProperty()
    final override var componentState: ComponentState by componentStateValue.asDelegate()

    override val children: ObservableList<InternalComponent> = persistentListOf<InternalComponent>().toProperty()

    // Common properties
    final override val themeProperty = metadata.themeProperty
    final override var theme: ColorTheme by themeProperty.asDelegate()

    final override val componentStyleSetProperty = metadata.componentStyleSetProperty
    final override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    final override val tilesetProperty = metadata.tileset.toProperty { oldValue, newValue ->
        oldValue isCompatibleWith newValue
    }.apply {
        bind(metadata.tilesetProperty)
    }
    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    final override val hiddenProperty = metadata.hiddenProperty
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    final override val disabledProperty = metadata.disabledProperty
    final override var isDisabled: Boolean by disabledProperty.asDelegate()


    // Misc
    final override val updateOnAttach = metadata.updateOnAttach

    init {
        val updateState: (Boolean) -> Unit = {
            componentState = if (it) {
                logger.debug("Component disabled. Applying disabled style.")
                DISABLED
            } else {
                logger.debug("Component enabled. Applying enabled style.")
                DEFAULT
            }
        }
        val updateTheme: (ColorTheme) -> Unit = {
            if (it.isNotUnknown) {
                componentStyleSet = convertColorTheme(it)
            }
        }
        val updateStyle: (ComponentStyleSet) -> Unit = {
            if (it.isNotUnknown) {
                componentStyleSet = it
            }
        }
        disabledProperty.onChange {
            updateState(it.newValue)
        }
        themeProperty.onChange {
            updateTheme(it.newValue)
        }
        componentStyleSetProperty.onChange {
            updateStyle(it.newValue)
        }
        updateState(isDisabled)
        if(componentStyleSet.isUnknown) {
            updateTheme(theme)
        }
    }

    @Synchronized
    override fun render(graphics: TileGraphics) {
        (renderer as ComponentRenderingStrategy<Component>).render(this, graphics)
    }

    // MOVABLE

    @Synchronized
    override fun moveTo(position: Position): Boolean {
        parent.map { parent ->
            val newBounds = movable.rect.withPosition(position)
            require(parent.containsBoundable(newBounds)) {
                "Can't move Component $this with new bounds $newBounds out of its parent's bounds $parent."
            }
        }
        val diff = position - absolutePosition
        movable.moveTo(position)
        relativePosition += diff
        return true
    }

    override fun moveBy(position: Position) = moveTo(this.position + position)

    override fun moveRightBy(delta: Int) = moveTo(position.withRelativeX(delta))

    override fun moveLeftBy(delta: Int) = moveTo(position.withRelativeX(-delta))

    override fun moveUpBy(delta: Int) = moveTo(position.withRelativeY(-delta))

    override fun moveDownBy(delta: Int) = moveTo(position.withRelativeY(delta))

    override fun asInternalComponent(): InternalComponent = this

    override fun clearCustomStyle() {
        componentStyleSet = ComponentStyleSet.defaultStyleSet()
    }

    final override fun requestFocus(): Boolean {
        Zircon.eventBus.publish(
            event = RequestFocusFor(this, this),
            eventScope = ZirconScope
        )
        return hasFocusValue.value
    }

    final override fun clearFocus() {
        Zircon.eventBus.publish(
            event = ClearFocus(this, this),
            eventScope = ZirconScope
        )
    }

    override fun focusGiven() = whenEnabled {
        updateComponentState(FOCUS_GIVEN)
        hasFocusValue.value = true
    }

    override fun focusTaken() = whenEnabled {
        updateComponentState(FOCUS_TAKEN)
        hasFocusValue.value = false
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
        other as DefaultComponent
        if (id != other.id) return false
        return true
    }

    final override fun hashCode(): Int {
        return id.hashCode()
    }

    private fun updateComponentState(eventType: EventType) {
        val key = ComponentStateKey(
            oldState = componentState,
            isFocused = hasFocusValue.value,
            eventType = eventType
        )
        logger.debug("Updating component state with $key.")
        COMPONENT_STATE_TRANSITIONS[key]?.let {
            logger.debug("Component state was updated to state $it.")
            componentState = it
        } ?: logger.debug("There was no corresponding key, no update happened.")
    }

    enum class EventType {
        FOCUS_GIVEN, FOCUS_TAKEN, MOUSE_ENTERED, MOUSE_EXITED, MOUSE_PRESSED, MOUSE_RELEASED, ACTIVATED, DEACTIVATED
    }

    data class ComponentStateKey(
        val oldState: ComponentState,
        val isFocused: Boolean,
        val eventType: EventType
    )

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
            // This happens when space is pressed on a component then the user presses tab (and focus is lost)
            ComponentStateKey(ACTIVE, true, FOCUS_TAKEN) to DEFAULT,
            // this happens when a component is removed when clicked in a HBox and
            // the next (to its right) component gets realigned
            ComponentStateKey(DEFAULT, false, MOUSE_RELEASED) to HIGHLIGHTED
        )
    }
}
