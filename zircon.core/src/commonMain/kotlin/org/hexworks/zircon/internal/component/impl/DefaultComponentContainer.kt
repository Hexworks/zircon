package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.behavior.ComponentFocusOrderList
import org.hexworks.zircon.internal.behavior.impl.DefaultComponentFocusOrderList
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.uievent.UIEventDispatcher
import org.hexworks.zircon.internal.uievent.impl.UIEventToComponentDispatcher
import kotlin.contracts.ExperimentalContracts
import kotlin.jvm.Synchronized

class DefaultComponentContainer(
    private val root: RootContainer,
    private val focusOrderList: ComponentFocusOrderList = DefaultComponentFocusOrderList(root),
    private val dispatcher: UIEventToComponentDispatcher = UIEventToComponentDispatcher(
        root = root,
        focusOrderList = focusOrderList
    )
) : InternalComponentContainer,
    ComponentContainer by root,
    ComponentFocusOrderList by focusOrderList,
    UIEventDispatcher by dispatcher {

    override val isActive = false.toProperty()
    override val flattenedTree: List<InternalComponent>
        get() = root.componentTree
    override val renderables: List<Renderable>
        get() = flattenedTree

    private val logger = LoggerFactory.getLogger(this::class)

    @OptIn(ExperimentalContracts::class)
    @Synchronized
    override fun dispatch(event: UIEvent): UIEventResponse {
        return if (isActive.value) {
            dispatcher.dispatch(event)
        } else Pass
    }


    @Synchronized
    override fun activate() {
        logger.debug("Activating component container.")
        isActive.value = true
        refreshFocusables()
        root.eventBus.simpleSubscribeTo<ComponentAdded>(root.eventScope) {
            refreshFocusables()
        }.keepWhile(isActive)
        root.eventBus.simpleSubscribeTo<ComponentRemoved>(root.eventScope) {
            refreshFocusables()
        }.keepWhile(isActive)
    }

    @Synchronized
    override fun deactivate() {
        isActive.value = false
    }

}
