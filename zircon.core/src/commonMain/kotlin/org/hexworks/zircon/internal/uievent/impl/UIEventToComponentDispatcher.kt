package org.hexworks.zircon.internal.uievent.impl

import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.ComponentEventType.*
import org.hexworks.zircon.api.uievent.KeyboardEventType.KEY_PRESSED
import org.hexworks.zircon.api.uievent.MouseEventType.*
import org.hexworks.zircon.api.uievent.UIEventPhase.*
import org.hexworks.zircon.internal.behavior.ComponentFocusOrderList
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.internal.event.ZirconEvent.ClearFocus
import org.hexworks.zircon.internal.event.ZirconEvent.RequestFocusFor
import org.hexworks.zircon.internal.uievent.UIEventDispatcher
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * This implementation of [UIEventDispatcher] dispatches [UIEvent]s
 * to [Component]s.
 */
public class UIEventToComponentDispatcher(
    private val root: RootContainer,
    private val focusOrderList: ComponentFocusOrderList
) : UIEventDispatcher {

    private var lastMousePosition = Position.UNKNOWN
    private var lastHoveredComponent: InternalComponent = root

    /**
     * The component that received the last [MOUSE_PRESSED] and has not seen its
     * [MOUSE_RELEASED] yet. While set, every [MOUSE_DRAGGED] and the [MOUSE_RELEASED]
     * go to it regardless of where the cursor is (the same implicit capture Compose,
     * JavaFX and Qt do), and no other component gets hover events.
     */
    private var capturedComponent: InternalComponent? = null

    /** Whether the cursor was inside [capturedComponent]'s bounds at the last captured event. */
    private var cursorInCaptured = false

    private val shortcutsConfig get() = ZirconDefaults.current.shortcutsConfig

    init {
        root.eventBus.simpleSubscribeTo(RequestFocusFor, root.eventScope) { (component) ->
            require(component is InternalComponent) {
                "Only InternalComponents can be focused."
            }
            focusComponent(component)
        }
        root.eventBus.simpleSubscribeTo(ClearFocus, root.eventScope) { (component) ->
            if (focusOrderList.isFocused(component as InternalComponent)) {
                focusComponent(root)
            }
        }
    }

    @OptIn(ExperimentalContracts::class)
    override fun dispatch(event: UIEvent): UIEventResponse {
        capturedComponent?.let { captured ->
            if (!captured.isAttached) {
                // The pressed component left the tree mid-drag, nothing to deliver to.
                capturedComponent = null
            } else if (event is MouseEvent && event.type in setOf(MOUSE_DRAGGED, MOUSE_RELEASED)) {
                return dispatchCaptured(captured, event)
            }
        }

        // we need to transform the mouse moved event in the case when the mouse
        // is exited a component and entered another
        if (mouseExitedComponent(event, lastMousePosition, lastHoveredComponent, root)) {
            // TODO: fix activation here as well
            logger.debug { "Mouse exited component $lastHoveredComponent..." }
            return handleHoveredComponentChange(event)
        }

        // otherwise business as usual

        return findTargetOrNull(event)?.let { target ->
            if (event is MouseEvent && event.type == MOUSE_PRESSED) {
                capturedComponent = target
                cursorInCaptured = true
            }
            dispatchTo(target, event)
        } ?: Pass
    }

    /**
     * Performs regular event propagation for [event] with [target], then tries to
     * transform it to component events and picks the result with the highest precedence.
     * Note that the result of the regular propagation can't influence the component
     * event propagation and vice-versa.
     */
    @ExperimentalContracts
    private fun dispatchTo(target: InternalComponent, event: UIEvent): UIEventResponse {
        val result = performEventPropagation(target, event)
        return if (result.shouldStopPropagation()) {
            result
        } else result.pickByPrecedence(performComponentEvents(target, event))
    }

    /**
     * Delivers a [MOUSE_DRAGGED] or [MOUSE_RELEASED] to the [captured] component.
     * The captured component still gets [MOUSE_EXITED] / [MOUSE_ENTERED] when the cursor
     * crosses its own bounds (a button un-highlights when dragged off it), but nothing
     * else does. On release the capture ends and hover is re-synced to whatever is
     * under the cursor.
     */
    @ExperimentalContracts
    private fun dispatchCaptured(captured: InternalComponent, event: MouseEvent): UIEventResponse {
        var result = syncCapturedBounds(captured, event)
        lastMousePosition = event.position
        result = result.pickByPrecedence(dispatchTo(captured, event))
        if (event.type == MOUSE_RELEASED) {
            capturedComponent = null
            result = result.pickByPrecedence(resyncHoverAfterRelease(captured, event))
        }
        return result
    }

    @ExperimentalContracts
    private fun syncCapturedBounds(captured: InternalComponent, event: MouseEvent): UIEventResponse {
        val nowIn = captured.containsPosition(event.position)
        if (nowIn == cursorInCaptured) return Pass
        cursorInCaptured = nowIn
        val type = if (nowIn) MOUSE_ENTERED else MOUSE_EXITED
        return dispatchTo(captured, event.copy(type = type))
    }

    @ExperimentalContracts
    private fun resyncHoverAfterRelease(captured: InternalComponent, event: MouseEvent): UIEventResponse {
        val underCursor = root.fetchComponentByPositionOrNull(event.position) ?: root
        if (underCursor.id == captured.id) {
            lastHoveredComponent = captured
            return Pass
        }
        var result: UIEventResponse = Pass
        if (cursorInCaptured) {
            // Released over a child or an overlapping component: the captured one is still
            // geometrically under the cursor but no longer the hovered one.
            result = dispatchTo(captured, event.copy(type = MOUSE_EXITED))
        }
        lastHoveredComponent = underCursor
        return result.pickByPrecedence(dispatchTo(underCursor, event.copy(type = MOUSE_ENTERED)))
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
                // A component's own shortcut wins over focus-based handling: activating and
                // deactivating in one go is the keyboard equivalent of a click.
                root.componentTree.firstOrNull { component ->
                    component.activationShortcut?.matches(event) == true && !component.isDisabled && !component.isHidden
                }?.let { shortcutTarget ->
                    return activateComponent(shortcutTarget).pickByPrecedence(deactivateComponent(shortcutTarget))
                }
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
        val exitedResponse = dispatch(
            event.copy(
                type = MOUSE_EXITED,
                position = lastMousePosition
            )
        )
        lastMousePosition = event.position
        root.fetchComponentByPositionOrNull(lastMousePosition)?.let {
            lastHoveredComponent = it
        }
        val enteredResponse = dispatch(
            event.copy(
                type = MOUSE_ENTERED,
                position = lastMousePosition
            )
        )
        return exitedResponse.pickByPrecedence(enteredResponse)
    }

    /**
     * Tries to find the target [Component] for the given [event].
     */
    private fun findTargetOrNull(event: UIEvent): InternalComponent? {
        return when (event) {
            is KeyboardEvent -> {
                focusOrderList.focusedComponent
            }

            is MouseEvent -> {
                root.fetchComponentByPositionOrNull(event.position)
            }

            else -> {
                null
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
        val pathToTarget = root.calculatePathTo(target).dropLast(1)
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
    public fun executePhase(
        component: InternalComponent,
        event: UIEvent,
        phase: UIEventPhase,
        previousResult: UIEventResponse
    ): UIEventResponse {
        var result = previousResult
        result = result.pickByPrecedence(component.process(event, phase))
        if (result.shouldStopPropagation()) {
//            logger.debug{"Propagation was stopped by component: $component.")
        } else if (result.allowsDefaults()) {
//            logger.debug{"Trying defaults for component $component, with event $event and phase $phase")
            result = result.pickByPrecedence(tryDefaultsFor(component, event, phase))
        }
        if (result != Pass) {
            // The component reacted to the event (typed a character, dragged a scroll bar...)
            // so whatever it draws from may have changed.
            component.invalidateRender()
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
//        logger.debug{"Trying to focus component $componentToFocus.")
        return if (focusOrderList.canFocus(componentToFocus)) {

//            logger.debug{"Component $componentToFocus can be focused, proceeding.")
            val currentlyFocusedComponent = focusOrderList.focusedComponent

//            logger.debug{"Taking focus from currently focused component $currentlyFocusedComponent.")
            val focusTaken = ComponentEvent(FOCUS_TAKEN)
            var takenResult = currentlyFocusedComponent.process(focusTaken, TARGET)
            if (takenResult.allowsDefaults()) {
//                logger.debug{"Default focus taken event was not prevented for component $currentlyFocusedComponent, proceeding.")
                takenResult = takenResult.pickByPrecedence(currentlyFocusedComponent.focusTaken())
            }

//            logger.debug{"Focusing new component $componentToFocus.")
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

    public companion object {

        internal val logger = LoggerFactory.getLogger(UIEventToComponentDispatcher::class)

    }
}

@ExperimentalContracts
private fun mouseExitedComponent(
    event: UIEvent,
    lastMousePosition: Position,
    lastHoveredComponent: Component,
    root: RootContainer
): Boolean {
    contract {
        returns(true) implies (event is MouseEvent)
    }
    return if (event is MouseEvent &&
        event.type in setOf(MOUSE_MOVED, MOUSE_DRAGGED) &&
        event.position != lastMousePosition
    ) {
        root.fetchComponentByPositionOrNull(event.position)?.let { currentComponent ->
            lastHoveredComponent.id != currentComponent.id
        }.orElse(false)
    } else false
}
