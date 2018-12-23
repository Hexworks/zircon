package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.ComponentFocusHandler
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope

class DefaultComponentFocusHandler(private val rootComponent: InternalComponent) : ComponentFocusHandler {

    override var focusedComponent: InternalComponent = rootComponent
        private set

    private val logger = LoggerFactory.getLogger(this::class)

    private val nextsLookup = mutableMapOf(Pair(rootComponent.id, rootComponent))
    private val prevsLookup = nextsLookup.toMutableMap()

    init {
        Zircon.eventBus.subscribe<ZirconEvent.RequestFocusFor>(ZirconScope) { (component) ->
            require(component is InternalComponent) {
                "Only InternalComponents can be focused."
            }
            focus(component)
        }
        Zircon.eventBus.subscribe<ZirconEvent.ClearFocus>(ZirconScope) { (component) ->
            if (focusedComponent.id == component.id) {
                focus(rootComponent)
            }
        }
    }

    override fun focusNext() {
        nextsLookup[focusedComponent.id]?.let { focus(it) }
    }

    override fun focusPrevious() {
        prevsLookup[focusedComponent.id]?.let { focus(it) }
    }

    override fun refreshFocusables() {
        nextsLookup.clear()
        prevsLookup.clear()

        val tree = rootComponent.toFlattenedComponents().filter { it.acceptsFocus() }
        if (tree.isNotEmpty()) {
            val first = tree.first()
            nextsLookup[rootComponent.id] = first
            prevsLookup[first.id] = rootComponent
            var prev = first

            tree.iterator().let { treeIter ->
                treeIter.next() // first already handled
                while (treeIter.hasNext()) {
                    val next = treeIter.next()
                    nextsLookup[prev.id] = next
                    prevsLookup[next.id] = prev
                    prev = next
                }
            }
            nextsLookup[prev.id] = rootComponent
            prevsLookup[rootComponent.id] = prev
        }
        focusedComponent = rootComponent
    }

    override fun focus(component: InternalComponent): Boolean {
        return if (canFocus(component)) {
            focusedComponent.takeFocus()
            focusedComponent = component
            component.giveFocus()
            true
        } else {
            false
        }
    }

    private fun canFocus(component: InternalComponent) =
            component.acceptsFocus() && isNotAlreadyFocused(component) && component.isAttached()

    private fun isNotAlreadyFocused(focusable: Focusable) =
            focusedComponent.id != focusable.id

}
