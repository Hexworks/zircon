package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved

class DefaultRootContainer(
    metadata: ComponentMetadata,
    override val application: Application,
    renderingStrategy: ComponentRenderingStrategy<RootContainer>,
) : RootContainer, DefaultContainer(
    metadata = metadata,
    renderer = renderingStrategy
) {

    override val componentTree = persistentListOf(this as InternalComponent).toProperty()

    init {
        eventBus.simpleSubscribeTo<ComponentAdded>(eventScope) { (parent, component) ->
            componentTree.transformValue { items ->
                val parentIdx = items.indexOf(parent)
                val result = if (parentIdx > -1) {
                    val itemsIdx = parentIdx + parent.nextSiblingIdx
                    items.addAll(itemsIdx, component.flattenedTree)
                } else items
                result
            }
        }
        eventBus.simpleSubscribeTo<ComponentRemoved>(eventScope) { (_, component) ->
            componentTree.transformValue { items ->
                val result = if (items.indexOf(component) > -1) {
                    val toRemove = component.flattenedTree
                    toRemove.forEach { it.root = null }
                    items.removeAll(toRemove)
                } else items
                result
            }
        }
    }

    override fun addComponent(component: Component): InternalAttachedComponent {
        val attachment = super<DefaultContainer>.addComponent(component)
        val ic = component.asInternalComponent()
        ic.flattenedTree.forEach {
            it.root = this
        }
        eventBus.publish(
            event = ComponentAdded(
                parent = this,
                component = ic,
                emitter = this
            ),
            eventScope = eventScope
        )
        return attachment
    }

    override fun acceptsFocus() = true

    override fun focusGiven() = Processed

    override fun focusTaken() = Processed

    override fun convertColorTheme(colorTheme: ColorTheme) = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = colorTheme.secondaryForegroundColor
            backgroundColor = colorTheme.secondaryBackgroundColor
        }
    }

    override fun calculatePathTo(component: InternalComponent): List<InternalComponent> {
        return componentTree.filter { it.containsBoundable(component) }
    }

    override fun fetchComponentByPositionOrNull(absolutePosition: Position): InternalComponent? =
        if (this.containsPosition(absolutePosition).not()) {
            null
        } else {
            componentTree.last { it.containsPosition(absolutePosition) }
        }

    private val InternalComponent.nextSiblingIdx: Int
        get() = children.size
}

