package org.hexworks.zircon.internal.component.data

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
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

/**
 * Contains all the common data for component builders.
 */
data class CommonComponentProperties<T : Component>(
    var name: String = "",
    var alignmentStrategy: AlignmentStrategy = ComponentAlignments.positionalAlignment(0, 0),
    var decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
    var preferredContentSize: Size = Size.unknown(),
    var preferredSize: Size = Size.unknown(),
    var componentRenderer: ComponentRenderer<out T> = NoOpComponentRenderer(),
    var postProcessors: List<ComponentPostProcessor<T>> = listOf(),
    var updateOnAttach: Boolean = true,
    // can be changed runtime
    val themeProperty: Property<ColorTheme> = ColorTheme.unknown().toProperty(),
    val componentStyleSetProperty: Property<ComponentStyleSet> = ComponentStyleSet.unknown().toProperty(),
    val tilesetProperty: Property<TilesetResource> = TilesetResource.unknown().toProperty(),
    val hiddenProperty: Property<Boolean> = false.toProperty(),
    val disabledProperty: Property<Boolean> = false.toProperty()
) {
    var theme: ColorTheme by themeProperty.asDelegate()
    var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()
    var tileset: TilesetResource by tilesetProperty.asDelegate()
    var isHidden: Boolean by hiddenProperty.asDelegate()
    var isDisabled: Boolean by disabledProperty.asDelegate()
}
