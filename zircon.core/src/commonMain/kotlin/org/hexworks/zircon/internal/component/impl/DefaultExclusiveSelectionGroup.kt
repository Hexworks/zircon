@file:Suppress("DEPRECATION")

package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.builder.component.group
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ExclusiveSelectionGroup
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalGroup

public class DefaultExclusiveSelectionGroup<T> internal constructor(
    initialIsDisabled: Boolean,
    initialIsHidden: Boolean,
    initialTheme: ColorTheme,
    initialTileset: TilesetResource,
    private val groupDelegate: InternalGroup<T> = group<T> {
        isDisabled = initialIsDisabled
        isHidden = initialIsHidden
        theme = initialTheme
        tileset = initialTileset
    } as InternalGroup<T>
) : InternalGroup<T> by groupDelegate, ExclusiveSelectionGroup<T> where T : Component, T : Selectable {

    private val members = mutableMapOf<String, Pair<T, Subscription>>()

    override val selectedProperty: Property<T?> = null.toProperty()
    override var selected: T?
        get() = selectedProperty.value
        set(value) {
            if (value == null) selectedProperty.value?.isSelected = false else value.isSelected = true
        }

    override fun addComponent(component: T): AttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(component.key.isNotBlank()) {
            "A member of an exclusive selection group needs a key: $component has none."
        }
        require(members.containsKey(component.key).not()) {
            "There is already a member with the key '${component.key}' in this group."
        }
        val handle = GroupAttachedComponent(component, this)
        members[component.key] = component to component.selectedProperty.onChange { (_, newlySelected) ->
            if (newlySelected) select(component)
            else if (selectedProperty.value === component) selectedProperty.value = null
        }
        if (component.isSelected) select(component)
        groupDelegate.addComponent(component)
        return handle
    }

    override fun addComponents(vararg components: T): List<AttachedComponent> = components.map(::addComponent)

    override fun removeComponent(component: Component) {
        members.remove((component as? Selectable)?.key)?.second?.dispose()
        if (selectedProperty.value === component) selectedProperty.value = null
        groupDelegate.removeComponent(component)
    }

    private fun select(component: T) {
        val previous = selectedProperty.value
        if (previous !== component) {
            selectedProperty.value = component
            previous?.isSelected = false
        }
    }
}
