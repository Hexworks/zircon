package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalGroup
import org.hexworks.zircon.internal.extensions.disposeAll

class DefaultGroup<T : Component> internal constructor(
    initialIsDisabled: Boolean,
    initialIsHidden: Boolean,
    initialTheme: ColorTheme,
    initialTileset: TilesetResource,
    override val name: String
) : InternalGroup<T> {

    private val componentBindings: ComponentBindings = mutableMapOf()

    override val disabledProperty = initialIsDisabled.toProperty()
    override val hiddenProperty = initialIsHidden.toProperty()
    override val themeProperty = initialTheme.toProperty()
    override val tilesetProperty = initialTileset.toProperty(validator = { oldValue, newValue ->
        oldValue isCompatibleWith newValue
    })

    override var isDisabled: Boolean by disabledProperty.asDelegate()
    override var isHidden: Boolean by hiddenProperty.asDelegate()
    override var theme: ColorTheme by themeProperty.asDelegate()
    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    override fun addComponent(component: T): AttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        return componentBindings[component.id]?.first ?: run {
            val handle = GroupAttachedComponent(component, this)
            componentBindings[component.id] = handle to mutableListOf(
                component.disabledProperty.updateFrom(disabledProperty),
                component.hiddenProperty.updateFrom(hiddenProperty),
                component.themeProperty.updateFrom(themeProperty),
                component.tilesetProperty.updateFrom(tilesetProperty)
            )
            handle
        }
    }

    override fun addComponents(vararg components: T) = components.map(::addComponent)

    override fun removeComponent(component: Component) {
        componentBindings.remove(component)
    }

}

private fun ComponentBindings.remove(component: Component) {
    remove(component.id)?.let { (_, bindings) ->
        bindings.disposeAll()
    }
}

private typealias ComponentBindings = MutableMap<UUID, Pair<AttachedComponent, MutableList<Binding<Any>>>>
