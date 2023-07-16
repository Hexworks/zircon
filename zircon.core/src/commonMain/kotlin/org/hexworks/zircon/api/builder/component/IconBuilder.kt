package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultIcon
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class IconBuilder : BaseComponentBuilder<Icon>(
    initialRenderer = DefaultIconRenderer()
) {

    var iconProperty: Property<Tile> = Tile.empty().toProperty()
    var iconTile: Tile
        get() = iconProperty.value
        set(value) {
            iconProperty.value = value
        }

    override fun build(): Icon {
        return DefaultIcon(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            iconProperty = iconProperty
        ).attachListeners()
    }
}

/**
 * Creates a new [Icon] using the component builder DSL and returns it.
 */
fun buildIcon(init: IconBuilder.() -> Unit): Icon =
    IconBuilder().apply(init).build()

/**
 * Creates a new [Icon] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Icon].
 */
fun <T : BaseContainerBuilder<*>> T.icon(
    init: IconBuilder.() -> Unit
): Icon = buildChildFor(this, IconBuilder(), init)
