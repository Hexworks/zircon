package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.orElseGet
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.dsl.component.buildRadioButton
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalGroup
import kotlin.jvm.Synchronized

class DefaultRadioButtonGroup internal constructor(
    initialIsDisabled: Boolean,
    initialIsHidden: Boolean,
    initialTheme: ColorTheme,
    initialTileset: TilesetResource,
    private val groupDelegate: InternalGroup<RadioButton> = Components.group<RadioButton>()
        .withIsDisabled(initialIsDisabled)
        .withIsHidden(initialIsHidden)
        .withTheme(initialTheme)
        .withTileset(initialTileset)
        .build() as InternalGroup<RadioButton>
) : RadioButtonGroup, InternalGroup<RadioButton> by groupDelegate {

    private val buttons = mutableMapOf<String, Pair<GroupAttachedComponent, Subscription>>()

    override var selectedButtonProperty: Property<RadioButton?> = null.toProperty()
    override var selectedButton: RadioButton? by selectedButtonProperty.asDelegate()

    @Synchronized
    override fun addComponent(component: RadioButton): AttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(buttons.containsKey(component.key).not()) {
            "There is already a Radio Button in this Radio Button Group with the key '${component.key}'."
        }
        val handle = GroupAttachedComponent(component, this)
        buttons[component.key] = handle to component.selectedProperty.onChange { (_, newlySelected) ->
            selectedButton?.let { previousSelected ->
                if (newlySelected && previousSelected !== component) {
                    previousSelected.isSelected = false
                    selectedButton = component
                }
            }.orElseGet {
                selectedButton = component
            }
        }
        if (component.isSelected) {
            selectedButton?.let { oldSelection ->
                oldSelection.isSelected = false
            }
            selectedButton = component
        }
        groupDelegate.addComponent(component)
        return handle
    }

    override fun addComponents(vararg components: RadioButton) = components.map(::addComponent)
}
