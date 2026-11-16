@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.hasNegativeComponent
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.component.theme.PinnedAspects
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ComponentPalette

/**
 * Contains metadata that is common to **all** [Component]s.
 */
public data class ComponentMetadata(
    // TODO: next big thing is to make these observable too
    val position: Position,
    val size: Size,
    val name: String = "",
    val bindingAction: BindingAction = UpdateOnBind,
    // properties
    val themeProperty: Property<ColorTheme> = ColorTheme.UNKNOWN.toProperty(),
    val componentStyleSetProperty: Property<ComponentStyleSet> = ComponentStyleSet.UNKNOWN.toProperty(),
    val tilesetProperty: Property<TilesetResource> = TilesetResource.unknown().toProperty(),
    val hiddenProperty: Property<Boolean> = false.toProperty(),
    val disabledProperty: Property<Boolean> = false.toProperty(),
    /**
     * A keyboard shortcut that activates the component from anywhere on its screen,
     * as if it was focused and the activation key was pressed. `null` means none.
     */
    val activationShortcut: KeyboardEventMatcher? = null,
    /**
     * What the component is for a [ComponentTheme]: the kind, or the composite it is a part of.
     */
    val role: ComponentRole = ComponentRole.NONE,
    /**
     * The component kind the builder made; the last fallback of a theme lookup.
     */
    val kind: ComponentRole = role,
    /**
     * The semantic weight within [role].
     */
    val variant: Variant = Variant.DEFAULT,
    /**
     * Which piece of a composite the component is.
     */
    val part: Part = Part.NONE,
    /**
     * Which way the component runs.
     */
    val orientation: Orientation = Orientation.HORIZONTAL,
    /**
     * The [ComponentTheme] the component starts with.
     */
    val componentThemeProperty: Property<ComponentTheme> = ZirconDefaults.current.componentTheme.toProperty(),
    /**
     * The [ComponentPalette] the component starts with.
     */
    val componentPaletteProperty: Property<ComponentPalette> = ZirconDefaults.current.componentPalette.toProperty(),
    /**
     * `true` when the component palette was set explicitly on the builder: a parent then does
     * not replace it on attach or when its own palette changes.
     */
    val componentPaletteExplicit: Boolean = false,
    /**
     * The looks the builder set explicitly, which a [ComponentTheme] leaves alone.
     */
    val pinnedAspects: PinnedAspects = PinnedAspects.NONE,
    /**
     * `true` when the component theme was set explicitly on the builder: a parent then does
     * not replace it on attach or when its own theme changes.
     */
    val componentThemeExplicit: Boolean = false,
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
        require(position.hasNegativeComponent.not()) {
            "Can't have a Component with a position ($position) which has a negative dimension."
        }
    }
}
