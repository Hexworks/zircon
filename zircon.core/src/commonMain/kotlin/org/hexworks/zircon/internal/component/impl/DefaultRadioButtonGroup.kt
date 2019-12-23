package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.Synchronized

class DefaultRadioButtonGroup(
        initialIsDisabled: Boolean,
        initialIsHidden: Boolean,
        initialTheme: ColorTheme,
        initialTileset: TilesetResource,
        private val groupDelegate: Group<RadioButton> = Components.group<RadioButton>()
                .withIsDisabled(initialIsDisabled)
                .withIsHidden(initialIsHidden)
                .withTheme(initialTheme)
                .withTileset(initialTileset)
                .build()) : RadioButtonGroup, Group<RadioButton> by groupDelegate {

    private val buttons = mutableMapOf<String, Pair<RadioButton, Subscription>>()

    override val selectedButtonProperty = createPropertyFrom(Maybe.empty<RadioButton>())
    override var selectedButton: Maybe<RadioButton> by selectedButtonProperty.asDelegate()

    @Synchronized
    override fun add(component: RadioButton) {
        require(buttons.containsKey(component.key).not()) {
            "There is already a Radio Button in this Radio Button Group with the key '${component.key}'."
        }
        buttons[component.key] = component to component.selectedProperty.onChange { (_, _, newlySelected) ->
            selectedButton.map { previousSelected ->
                if (newlySelected && previousSelected !== component) {
                    previousSelected.isSelected = false
                    selectedButton = Maybe.of(component)
                }
            }.orElseGet {
                selectedButton = Maybe.of(component)
            }
        }
        if (component.isSelected) {
            selectedButton.map { oldSelection ->
                oldSelection.isSelected = false
            }
            selectedButton = Maybe.of(component)
        }
        groupDelegate.add(component)
    }

    @Synchronized
    override fun addAll(vararg components: RadioButton) = components.forEach(::add)

    @Synchronized
    override fun remove(component: RadioButton) {
        buttons.remove(component.key)?.second?.cancel()
        groupDelegate.remove(component)
    }

    @Synchronized
    override fun removeAll(vararg components: RadioButton) = components.forEach(::remove)
}
