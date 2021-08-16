package org.hexworks.zircon.api.component.data

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Contains metadata that is common to **all** [Component]s.
 */
// TODO: add API breakage to release notes
data class ComponentMetadata(
    // TODO: next big thing is to make these observable too
    val relativePosition: Position,
    val size: Size,
    val name: String = "",
    val updateOnAttach: Boolean = true,
    // properties
    val themeProperty: Property<ColorTheme> = ColorTheme.unknown().toProperty(),
    val componentStyleSetProperty: Property<ComponentStyleSet> = ComponentStyleSet.unknown().toProperty(),
    val tilesetProperty: Property<TilesetResource> = TilesetResource.unknown().toProperty(),
    val hiddenProperty: Property<Boolean> = false.toProperty(),
    val disabledProperty: Property<Boolean> = false.toProperty()
) {

    val tileset: TilesetResource
        get() = tilesetProperty.value

    val componentStyleSet: ComponentStyleSet
        get() = componentStyleSetProperty.value

    val theme: ColorTheme
        get() = themeProperty.value

    val isHidden: Boolean
        get() = hiddenProperty.value

    val isDisabled: Boolean
        get() = disabledProperty.value


    init {
        require(relativePosition.hasNegativeComponent.not()) {
            "Can't have a Component with a relative position ($relativePosition) which has a " +
                    "negative dimension."
        }
    }
}
