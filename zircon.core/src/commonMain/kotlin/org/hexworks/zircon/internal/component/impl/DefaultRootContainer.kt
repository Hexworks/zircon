package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.event.ZirconScope

class DefaultRootContainer(
        componentMetadata: ComponentMetadata,
        renderingStrategy: ComponentRenderingStrategy<RootContainer>
) : RootContainer, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy
) {

    override val componentTree = persistentListOf(this as InternalComponent).toProperty()

    init {
        Zircon.eventBus.simpleSubscribeTo<ComponentAdded>(ZirconScope) { (parent, component) ->
            componentTree.transformValue { items ->
                val parentIdx = items.indexOf(parent)
                val result = if (parentIdx > -1) {
                    val itemsIdx = parentIdx + parent.nextSiblingIdx
                    items.addAll(itemsIdx, component.otherComponentTree.apply {
                        root = Maybe.of(this@DefaultRootContainer)
                    })
                } else items
                result
            }
        }
        Zircon.eventBus.simpleSubscribeTo<ComponentRemoved>(ZirconScope) { (_, component) ->
            componentTree.transformValue { items ->
                val result = if (items.indexOf(component) > -1) {
                    items.removeAll(component.otherComponentTree.apply {
                        root = Maybe.empty()
                    })
                } else items
                result
            }
        }
    }

    override fun acceptsFocus() = true

    override fun focusGiven() = Processed

    override fun focusTaken() = Processed

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                    .build())
            .build()

    override fun calculatePathTo(component: InternalComponent): List<InternalComponent> {
        return componentTree.filter { it.containsBoundable(component) }
    }

    override fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent> =
            if (this.containsPosition(absolutePosition).not()) {
                Maybe.empty()
            } else {
                Maybe.of(componentTree.last { it.containsPosition(absolutePosition) })
            }

    private val InternalComponent.otherComponentTree: Collection<InternalComponent>
        get() = listOf(this) + children.map { it.asInternalComponent() }.flatMap { it.otherComponentTree }

    private val InternalComponent.nextSiblingIdx: Int
        get() = children.size
}

