package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(
    componentMetadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<out Component>
) : InternalContainer, DefaultComponent(
    componentMetadata = componentMetadata,
    renderer = renderer
) {

    final override val children: ObservableList<InternalComponent> by lazy {
        persistentListOf<InternalComponent>().toProperty()
    }

    // TODO: refactor this so that recursive changes are not necessary
    @Synchronized
    final override fun moveTo(position: Position): Boolean {
        val diff = position - this.position
        super.moveTo(position)
        children.forEach {
            it.moveTo(it.position + diff)
        }
        return true
    }

    /**
     * Note that this method can be overridden we'd like to advise against if it is possible.
     * The logic is complex and you can easily get into a sorry state where the implementation
     * doesn't make sense. If you really need to override this please call `super.addComponent`
     * and let Zircon do the heavy lifting for you.
     */
    @Synchronized
    override fun addComponent(component: Component): InternalAttachedComponent {
        val ic = checkIfCanAdd(component)
        val attachment = DefaultAttachedComponent(ic, this)

        children.add(ic)

        Zircon.eventBus.publish(
            event = ComponentAdded(
                parent = this,
                component = component.asInternalComponent(),
                emitter = this
            ),
            eventScope = ZirconScope
        )

        return attachment
    }

    final override fun asInternalComponent(): InternalContainer = this

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSet.empty()

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    private fun checkIfCanAdd(component: Component): InternalComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(component !== this) {
            "You can't add a component to itself."
        }
        require(component.isAttached.not()) {
            "This component is already attached to a parent. Please detach it first."
        }
        val originalRect = component.rect
        component.moveTo(component.absolutePosition + contentOffset + absolutePosition)
        if (RuntimeConfig.config.shouldCheckBounds()) {
            val contentBounds = contentSize.toRect()
            tileset.checkCompatibilityWith(component.tileset)
            require(contentBounds.containsBoundable(originalRect)) {
                "Adding out of bounds component $component " +
                        "with bounds $originalRect to the container $this " +
                        "with content bounds $contentBounds is not allowed."
            }
            children.firstOrNull { it.intersects(component) }?.let {
                throw IllegalArgumentException(
                    "You can't add a component to a container which intersects with other components. " +
                            "$it is intersecting with $component."
                )
            }
        }
        return component
    }

    private inner class DefaultAttachedComponent(
        override val component: InternalComponent,
        override val parentContainer: InternalContainer
    ) : InternalAttachedComponent, InternalComponent by component {

        init {
            component.parent = Maybe.of(parentContainer)
            component.disabledProperty
                .updateFrom(
                    observable = parentContainer.disabledProperty
                )
                .keepWhile(component.hasParent)
            component.hiddenProperty
                .updateFrom(
                    observable = parentContainer.hiddenProperty
                )
                .keepWhile(component.hasParent)
            component.themeProperty
                .updateFrom(
                    observable = parentContainer.themeProperty,
                    updateWhenBound = theme == ColorThemes.defaultTheme()
                )
                .keepWhile(component.hasParent)
            component.tilesetProperty
                .updateFrom(
                    observable = parentContainer.tilesetProperty
                )
                .keepWhile(component.hasParent)
        }

        @Synchronized
        override fun detach(): Component {
            component.parent = Maybe.empty()
            this@DefaultContainer.children.remove(component)
            Zircon.eventBus.publish(
                event = ComponentRemoved(
                    parent = parentContainer,
                    component = component,
                    emitter = this
                ),
                eventScope = ZirconScope
            )
            return component
        }
    }
}
