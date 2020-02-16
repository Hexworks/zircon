package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.internal.behavior.ComponentFocusOrderList
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.RootContainer

class DefaultComponentFocusOrderList(
        private val rootComponent: RootContainer
) : ComponentFocusOrderList {

    override var focusedComponent: InternalComponent = rootComponent
        private set

    private val nextsLookup: MutableMap<UUID, InternalComponent> = mutableMapOf(rootComponent.id to rootComponent)
    private val prevsLookup: MutableMap<UUID, InternalComponent> = nextsLookup.toMutableMap()

    override fun findNext() = nextsLookup.getValue(focusedComponent.id)

    override fun findPrevious() = prevsLookup.getValue(focusedComponent.id)

    override fun focus(component: InternalComponent) {
        logger.debug("Trying to focus component: $component.")
        if (canFocus(component)) {
            logger.debug("Component $component is focusable, focusing.")
            focusedComponent = component
        }
    }

    override fun refreshFocusables() {
        logger.debug {
            val prevTree = nextsLookup.keys.map { it.abbreviate() }
            val oldNexts = nextsLookup.map { "${it.key.abbreviate()}->${it.value.id.abbreviate()}" }.joinToString()
            val oldPrevs = prevsLookup.map { "${it.key.abbreviate()}->${it.value.id.abbreviate()}" }.joinToString()
            "Refreshing focusables. Prev tree: $prevTree, Old nexts: $oldNexts, Old prevs: $oldPrevs"
        }
        nextsLookup.clear()
        prevsLookup.clear()

        // this will have at least 1 element because the root container is always focusable
        val tree = rootComponent.descendants.filter { it.acceptsFocus() }
        logger.debug("New tree is ${tree.joinToString { it.id.abbreviate() }}, root is: ${rootComponent.id.abbreviate()}")

        var previous: InternalComponent = rootComponent

        tree.forEach { next ->
            logger.debug("Next for ${previous.id.abbreviate()} is ${next.id.abbreviate()}, " +
                    "previous for ${next.id.abbreviate()} is ${previous.id.abbreviate()}")
            nextsLookup[previous.id] = next
            prevsLookup[next.id] = previous
            previous = next
        }

        // root has children
        if (tree.isNotEmpty()) {
            logger.debug("Root, has children, adding circle between root and last.")
            // we make a connection between the first (root) and the last to make it circular
            logger.debug("Next for ${tree.last().id.abbreviate()} is ${rootComponent.id.abbreviate()}, " +
                    "prev for ${rootComponent.id.abbreviate()} is ${tree.last().id.abbreviate()}")
            nextsLookup[tree.last().id] = rootComponent
            prevsLookup[rootComponent.id] = tree.last()
        }

        // if the previously focused component was removed we reset the focus to the root
        if (tree.contains(focusedComponent).not()) {
            logger.debug("Tree doesn't contain previously focused component, focusing root.")
            focusedComponent = rootComponent
        }
    }

    override fun canFocus(component: InternalComponent): Boolean {
        val result = component.acceptsFocus() && isNotAlreadyFocused(component) && component.isAttached
        logger.debug {
            "Determining whether $component can be focused. Accepts focus? ${component.acceptsFocus()} " +
                    "Is not already focused? ${isNotAlreadyFocused(component)} " +
                    "Is attached? ${component.isAttached} Result: $result"
        }
        return result
    }

    private fun isNotAlreadyFocused(focusable: Focusable) =
            focusedComponent.id != focusable.id

    companion object {

        private val logger = LoggerFactory.getLogger(ComponentFocusOrderList::class)
    }
}
