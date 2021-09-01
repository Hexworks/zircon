package org.hexworks.zircon.examples.extensions

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.builder.fragment.SelectorBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.internal.resource.ColorThemeResource

private val THEMES = ColorThemeResource.values().map { it.getTheme() }.toProperty()

/**
 * Creates a [SelectorBuilder] pre-configured for [ColorTheme]s.
 */
fun Fragments.colorThemeSelector() = SelectorBuilder.newBuilder<ColorTheme>()
    .withValues(THEMES)
    .withToStringMethod { it.name }

/**
 * Creates a [SelectorBuilder] pre-configured for [TilesetResource]s.
 */
@JvmOverloads
fun Fragments.tilesetSelector(width: Int = 16, height: Int = width): SelectorBuilder<TilesetResource> {
    val size = Size.create(width, height)
    return SelectorBuilder.newBuilder<TilesetResource>()
        .withValues(
            BuiltInCP437TilesetResource.values()
                .filter { it.size == size }.plus(BuiltInTrueTypeFontResource
                    .values()
                    .map { it.toTilesetResource(height) }
                    .filter { it.size == size })
                .toProperty()
        )
}

fun <T : Any> Selector<T>.updateFromSelection(property: Property<T>): Selector<T> = also {
    property.updateFrom(selectedValue)
}
