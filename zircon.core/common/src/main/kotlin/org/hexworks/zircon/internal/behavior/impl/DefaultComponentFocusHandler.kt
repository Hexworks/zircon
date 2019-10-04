package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.ComponentFocusHandler
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope

class DefaultComponentFocusHandler(private val rootComponent: InternalContainer) : ComponentFocusHandler {

    override var focusedComponent: InternalComponent = rootComponent
        private set

    private val nextsLookup: MutableMap<Identifier, InternalComponent>
            = mutableMapOf(Pair(rootComponent.id, rootComponent))
    private val prevsLookup: MutableMap<Identifier, InternalComponent>
            = nextsLookup.toMutableMap()

    override fun findNext() = Maybe.ofNullable(nextsLookup[focusedComponent.id])

    override fun findPrevious() = Maybe.ofNullable(prevsLookup[focusedComponent.id])

    override fun focus(component: InternalComponent) {
        if (canFocus(component)) {
            focusedComponent = component
        }
    }

    override fun refreshFocusables() {
        nextsLookup.clear()
        prevsLookup.clear()

        val tree = rootComponent.descendants.filter { it.acceptsFocus() }
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

    override fun canFocus(component: InternalComponent) =
            component.acceptsFocus() && isNotAlreadyFocused(component) && component.isAttached()

    private fun isNotAlreadyFocused(focusable: Focusable) =
            focusedComponent.id != focusable.id

}
