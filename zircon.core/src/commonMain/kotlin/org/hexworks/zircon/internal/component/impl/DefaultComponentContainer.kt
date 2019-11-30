package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.ComponentFocusHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultComponentFocusHandler
import org.hexworks.zircon.internal.component.ComponentContainerState.ACTIVE
import org.hexworks.zircon.internal.component.ComponentContainerState.INACTIVE
import org.hexworks.zircon.internal.component.ComponentContainerState.INITIALIZING
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.extensions.cancelAll
import org.hexworks.zircon.internal.uievent.UIEventDispatcher
import org.hexworks.zircon.internal.uievent.impl.UIEventToComponentDispatcher
import kotlin.contracts.ExperimentalContracts
import kotlin.jvm.Synchronized

class DefaultComponentContainer(
        private val root: RootContainer,
        private val focusHandler: ComponentFocusHandler = DefaultComponentFocusHandler(root),
        private val dispatcher: UIEventToComponentDispatcher = UIEventToComponentDispatcher(
                root = root,
                focusHandler = focusHandler)) :
        InternalComponentContainer,
        ComponentContainer by root,
        ComponentFocusHandler by focusHandler,
        UIEventDispatcher by dispatcher {

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = root.flattenedTree.flatMap { it.layerStates }

    private val subscriptions = mutableListOf<Subscription>()
    private val logger = LoggerFactory.getLogger(this::class)
    private var state = INITIALIZING

    @ExperimentalContracts
    @Synchronized
    override fun dispatch(event: UIEvent): UIEventResponse {
        return if (isActive()) {
            dispatcher.dispatch(event)
        } else Pass
    }

    @Synchronized
    override fun addComponent(component: Component) {
        require(component is InternalComponent) {
            "Adding a component which does not implement InternalComponent " +
                    "to a ComponentContainer is not allowed."
        }
        root.addComponent(component)
        refreshFocusables()
    }

    @Synchronized
    override fun removeComponent(component: Component): Boolean {
        return root.removeComponent(component).also {
            refreshFocusables()
        }
    }

    override fun isActive() = state == ACTIVE

    @Synchronized
    override fun activate() {
        logger.debug("Activating component container.")
        refreshFocusables()
        subscriptions.add(Zircon.eventBus.subscribe<ComponentAdded>(ZirconScope) {
            refreshFocusables()
        })
        subscriptions.add(Zircon.eventBus.subscribe<ComponentRemoved>(ZirconScope) {
            refreshFocusables()
        })
        state = ACTIVE
    }

    @Synchronized
    override fun deactivate() {
        subscriptions.cancelAll()
        dispatcher.focusComponent(root)
        state = INACTIVE
    }

}
