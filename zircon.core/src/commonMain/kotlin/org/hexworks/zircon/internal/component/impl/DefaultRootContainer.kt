package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.simpleSubscribeTo
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

class DefaultRootContainer(
        componentMetadata: ComponentMetadata,
        renderingStrategy: ComponentRenderingStrategy<RootContainer>
) : RootContainer, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val componentTree: PersistentList<InternalComponent>
        get() = flattenedTree.value

    /**
     * Holds the component tree rooted at this [RootContainer] flattened into an [ObservableList].
     * Note that [flattenedTree] also contains `this` (the root) as the first element.
     */
    private val flattenedTree: ListProperty<InternalComponent> by lazy {
        persistentListOf(this as InternalComponent).toProperty()
    }

    init {
        render()
        Zircon.eventBus.simpleSubscribeTo<ZirconEvent.ComponentAdded>(ZirconScope) { (parent, component) ->
            flattenedTree.transformValue { items ->
                val parentIdx = items.indexOf(parent)
                val result = if (parentIdx > -1) {
                    val itemsIdx = parentIdx + parent.nextSiblingIdx
                    val tree = component.componentTree
                    items.addAll(itemsIdx, tree)
                } else items
                result
            }
        }
        Zircon.eventBus.simpleSubscribeTo<ZirconEvent.ComponentRemoved>(ZirconScope) { (_, component) ->
            flattenedTree.transformValue { items ->
                val result = if (items.indexOf(component) > -1) {
                    val tree = component.componentTree
                    items.removeAll(tree)
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

    override fun fetchLayerStates(): Sequence<LayerState> = sequence {
        flattenedTree.value.forEach { component ->
            component.layerStates.forEach { yield(it) }
        }
    }

    @Synchronized
    override fun addComponent(component: Component): InternalAttachedComponent {
        return RootAttachment(super<DefaultContainer>.addComponent(component))
    }

    private inner class RootAttachment(
            val backend: InternalAttachedComponent
    ) : InternalAttachedComponent by backend {

        init {
            backend.component.componentTree.forEach {
                it.root = Maybe.of(this@DefaultRootContainer)
            }
        }

        @Synchronized
        override fun detach(): Component {
            return backend.detach().also { component ->
                component.asInternalComponent().componentTree.forEach {
                    it.root = Maybe.empty()
                }
            }
        }
    }

    // TODO: use flattenedTree instead of recursion
    private val InternalComponent.componentTree: Collection<InternalComponent>
        get() = listOf(this) + children.map { it.asInternalComponent() }.flatMap { it.componentTree }

    private val InternalComponent.nextSiblingIdx: Int
        get() = children.size
}

