package org.hexworks.zircon.internal.component.data

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.api.component.theme.PinnedAspects
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.data.Orientation

/**
 * Contains all the common data for component builders.
 */
public data class CommonComponentProperties<T : Component>(
    var name: String = "",
    var alignmentStrategy: AlignmentStrategy = ComponentAlignments.positionalAlignment(0, 0),
    var decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
    var preferredContentSize: Size = Size.UNKNOWN,
    var preferredSize: Size = Size.UNKNOWN,
    var componentRenderer: ComponentRenderer<out T> = NoOpComponentRenderer(),
    var postProcessors: List<ComponentPostProcessor<T>> = listOf(),
    var bindingAction: BindingAction = UpdateOnBind,
    val kind: ComponentRole = ComponentRole.NONE,
    var role: ComponentRole = ComponentRole.NONE,
    var variant: Variant = Variant.DEFAULT,
    var part: Part = Part.NONE,
    var orientation: Orientation = Orientation.HORIZONTAL,
    var pinnedAspects: PinnedAspects = PinnedAspects.NONE,
    var componentThemeExplicit: Boolean = false,
    val componentThemeProperty: Property<ComponentTheme?> = null.toProperty(),
    // can be changed runtime
    val themeProperty: Property<ColorTheme> = ColorTheme.UNKNOWN.toProperty(),
    val componentStyleSetProperty: Property<ComponentStyleSet> = ComponentStyleSet.UNKNOWN.toProperty(),
    val tilesetProperty: Property<TilesetResource> = TilesetResource.unknown().toProperty(),
    val hiddenProperty: Property<Boolean> = false.toProperty(),
    val disabledProperty: Property<Boolean> = false.toProperty()
) {
    var theme: ColorTheme by themeProperty.asDelegate()
    var componentTheme: ComponentTheme? by componentThemeProperty.asDelegate()
    var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()
    var tileset: TilesetResource by tilesetProperty.asDelegate()
    var isHidden: Boolean by hiddenProperty.asDelegate()
    var isDisabled: Boolean by disabledProperty.asDelegate()
}
