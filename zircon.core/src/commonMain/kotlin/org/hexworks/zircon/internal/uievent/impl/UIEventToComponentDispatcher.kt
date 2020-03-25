package org.hexworks.zircon.internal.uievent.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED
import org.hexworks.zircon.api.uievent.ComponentEventType.DEACTIVATED
import org.hexworks.zircon.api.uievent.ComponentEventType.FOCUS_GIVEN
import org.hexworks.zircon.api.uievent.ComponentEventType.FOCUS_TAKEN
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase.BUBBLE
import org.hexworks.zircon.api.uievent.UIEventPhase.CAPTURE
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.ComponentFocusOrderList
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent.ClearFocus
import org.hexworks.zircon.internal.event.ZirconEvent.RequestFocusFor
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.uievent.UIEventDispatcher
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.jvm.JvmSynthetic

/**
 * This implementation of [UIEventDispatcher] dispatches [UIEvent]s
 * to [Component]s.
 */
class UIEventToComponentDispatcher(
        private val root: InternalContainer,
        private val focusOrderList: ComponentFocusOrderList
) : UIEventDispatcher {

    private var lastMousePosition = Position.unknown()
    private var lastHoveredComponent: InternalComponent = root

    private val shortcutsConfig = RuntimeConfig.config.shortcutsConfig

    init {
        Zircon.eventBus.simpleSubscribeTo<RequestFocusFor>(ZirconScope) { (component) ->
            require(component is InternalComponent) {
                "Only InternalComponents can be focused."
            }
            focusComponent(component)
        }
        Zircon.eventBus.simpleSubscribeTo<ClearFocus>(ZirconScope) { (component) ->
            if (focusOrderList.isFocused(component as InternalComponent)) {
                focusComponent(root)
            }
        }
    }

    @ExperimentalContracts
    override fun dispatch(event: UIEvent): UIEventResponse {
        // we need to transform the mouse moved event in the case when the mouse
        // is exited a component and entered another
        if (mouseExitedComponent(event, lastMousePosition, lastHoveredComponent, root)) {
            // TODO: fix activation here as well
            logger.debug("Mouse exited component $lastHoveredComponent...")
            return handleHoveredComponentChange(event)
        }

        // otherwise business as usual

        return findTarget(event).map { target ->
            // we perform regular event propagation, then try to transform the event
            // to component events and we pick the result which has highest precedence
            // note that the result of the regular propagation can't influence the
            // component event propagation and vice-versa
            val result = performEventPropagation(target, event)
            if (result.shouldStopPropagation()) {
                result
            } else result.pickByPrecedence(performComponentEvents(target, event))
        }.orElse(Pass)
    }

    /**
     * Tries to transform the given [event] to [ComponentEvent]s and delegates it to
     * the given [target] [Component].
     */
    private fun performComponentEvents(target: InternalComponent, event: UIEvent): UIEventResponse {
        return when (event) {
            is MouseEvent -> {
                when (event.type) {
                    MOUSE_PRESSED -> focusComponent(target).pickByPrecedence(activateComponent(target))
                    MOUSE_RELEASED -> deactivateComponent(target)
                    else -> Pass
                }
                Pass
            }
            is KeyboardEvent -> {
                when {
                    shortcutsConfig.activateFocused.matches(event) -> {
                        activateComponent(focusOrderList.focusedComponent)
                    }
                    shortcutsConfig.deactivateActivated.matches(event) -> {
                        deactivateComponent(focusOrderList.focusedComponent)
                    }
                    shortcutsConfig.focusNext.matches(event) -> {
                        focusComponent(focusOrderList.findNext())
                    }
                    shortcutsConfig.focusPrevious.matches(event) -> {
                        focusComponent(focusOrderList.findPrevious())
                    }
                    else -> Pass
                }
            }
            else -> Pass
        }
    }

    /**
     * Transforms the [MOUSE_MOVED] event to a [MOUSE_EXITED] and a [MOUSE_ENTERED]
     * event.
     */
    @ExperimentalContracts
    private fun handleHoveredComponentChange(event: MouseEvent): UIEventResponse {
        val exitedResponse = dispatch(event.copy(
                type = MOUSE_EXITED,
                position = lastMousePosition))
        lastMousePosition = event.position
        root.fetchComponentByPosition(lastMousePosition).map {
            lastHoveredComponent = it
        }
        val enteredResponse = dispatch(event.copy(
                type = MOUSE_ENTERED,
                position = lastMousePosition))
        return exitedResponse.pickByPrecedence(enteredResponse)
    }

    /**
     * Tries to find the target [Component] for the given [event].
     */
    private fun findTarget(event: UIEvent): Maybe<out InternalComponent> {
        return when (event) {
            is KeyboardEvent -> {
                Maybe.of(focusOrderList.focusedComponent)
            }
            is MouseEvent -> {
                root.fetchComponentByPosition(event.position)
            }
            else -> {
                Maybe.empty()
            }
        }
    }

    /**
     * Performs event propagation with the given [target] and [event], eg: traverses the
     * [Component]s from the root to the target with the [CAPTURE] phase, calls the [target]
     * with the event and then performs the [BUBBLE] phase.
     */
    @ExperimentalContracts
    private fun performEventPropagation(target: InternalComponent, event: UIEvent): UIEventResponse {
        var finalResult: UIEventResponse = Pass
        val pathToTarget = target.calculatePathFromRoot().toList().dropLast(1)
        pathToTarget.forEach { component ->
            finalResult = executePhase(component, event, CAPTURE, finalResult)
            if (finalResult.shouldStopPropagation()) return finalResult
        }
        finalResult = executePhase(target, event, TARGET, finalResult)
        if (finalResult.shouldStopPropagation()) return finalResult
        for (component in pathToTarget.reversed()) {
            finalResult = executePhase(component, event, BUBBLE, finalResult)
            if (finalResult.shouldStopPropagation()) return finalResult
        }
        return finalResult
    }

    /**
     * Executes a phase and returns its result. An [UIEventPhase] consists of
     * - delegating the [UIEvent] to the [component] for processing
     * - and if its result is not [StopPropagation] or [PreventDefault]
     *   trying the default actions.
     */
    @ExperimentalContracts
    fun executePhase(component: InternalComponent, event: UIEvent, phase: UIEventPhase, previousResult: UIEventResponse): UIEventResponse {
        var result = previousResult
        result = result.pickByPrecedence(component.process(event, phase))
        if (result.shouldStopPropagation()) {
//            logger.debug("Propagation was stopped by component: $component.")
        } else if (result.allowsDefaults()) {
//            logger.debug("Trying defaults for component $component, with event $event and phase $phase")
            result = result.pickByPrecedence(tryDefaultsFor(component, event, phase))
        }
        return result
    }

    /**
     * Tries the default actions for the given [component], [event] and [phase].
     */
    @ExperimentalContracts
    private fun tryDefaultsFor(component: InternalComponent, event: UIEvent, phase: UIEventPhase): UIEventResponse {
        return when (event) {
            is MouseEvent -> {
                when (event.type) {
                    MOUSE_CLICKED -> component.mouseClicked(event, phase)
                    MOUSE_PRESSED -> component.mousePressed(event, phase)
                    MOUSE_RELEASED -> component.mouseReleased(event, phase)
                    MOUSE_ENTERED -> component.mouseEntered(event, phase)
                    MOUSE_EXITED -> component.mouseExited(event, phase)
                    MOUSE_WHEEL_ROTATED_UP -> component.mouseWheelRotatedUp(event, phase)
                    MOUSE_WHEEL_ROTATED_DOWN -> component.mouseWheelRotatedDown(event, phase)
                    MOUSE_DRAGGED -> component.mouseDragged(event, phase)
                    MOUSE_MOVED -> component.mouseMoved(event, phase)
                }
            }
            is KeyboardEvent -> {
                when (event.type) {
                    KEY_PRESSED -> component.keyPressed(event, phase)
                    KeyboardEventType.KEY_TYPED -> component.keyTyped(event, phase)
                    KeyboardEventType.KEY_RELEASED -> component.keyReleased(event, phase)
                }
            } // we don't handle component events here, since they are originated elsewhere. See below.
            else -> Pass
        }
    }

    /**
     * Performs focus change for the given [componentToFocus] [Component] if it
     * can be focused (eg: it is focusable, and it is not already focused).
     */
    private fun focusComponent(componentToFocus: InternalComponent): UIEventResponse {
//        logger.debug("Trying to focus component $componentToFocus.")
        return if (focusOrderList.canFocus(componentToFocus)) {

//            logger.debug("Component $componentToFocus can be focused, proceeding.")
            val currentlyFocusedComponent = focusOrderList.focusedComponent

//            logger.debug("Taking focus from currently focused component $currentlyFocusedComponent.")
            val focusTaken = ComponentEvent(FOCUS_TAKEN)
            var takenResult = currentlyFocusedComponent.process(focusTaken, TARGET)
            if (takenResult.allowsDefaults()) {
//                logger.debug("Default focus taken event was not prevented for component $currentlyFocusedComponent, proceeding.")
                takenResult = takenResult.pickByPrecedence(currentlyFocusedComponent.focusTaken())
            }

//            logger.debug("Focusing new component $componentToFocus.")
            focusOrderList.focus(componentToFocus)

            val focusGiven = ComponentEvent(FOCUS_GIVEN)
            var givenResult = componentToFocus.process(focusGiven, TARGET)
            if (givenResult.allowsDefaults()) {
                givenResult = givenResult.pickByPrecedence(componentToFocus.focusGiven())
            }
            takenResult.pickByPrecedence(givenResult)
        } else Pass
    }

    /**
     * Performs activation for the given [target] [Component].
     */
    private fun activateComponent(target: InternalComponent): UIEventResponse {
        var result = target.process(ComponentEvent(ACTIVATED), TARGET)
        if (result.allowsDefaults()) {
            result = result.pickByPrecedence(target.activated())
        }
        return result
    }

    private fun deactivateComponent(target: InternalComponent): UIEventResponse {
        var result = target.process(ComponentEvent(DEACTIVATED), TARGET)
        if (result.allowsDefaults()) {
            result = result.pickByPrecedence(target.deactivated())
        }
        return result
    }

    companion object {

        @JvmSynthetic
        internal val logger = LoggerFactory.getLogger(UIEventToComponentDispatcher::class)

    }
}

@ExperimentalContracts
private fun mouseExitedComponent(
        event: UIEvent,
        lastMousePosition: Position,
        lastHoveredComponent: Component,
        root: InternalContainer
): Boolean {
    contract {
        returns(true) implies (event is MouseEvent)
    }
    return if (event is MouseEvent &&
            event.type in setOf(MOUSE_MOVED, MOUSE_DRAGGED) &&
            event.position != lastMousePosition) {
        root.fetchComponentByPosition(event.position).map { currentComponent ->
            lastHoveredComponent.id != currentComponent.id
        }.orElse(false)
    } else false
}
