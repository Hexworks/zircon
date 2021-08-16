package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.component.impl.DefaultIcon
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class IconBuilder : BaseComponentBuilder<Icon, IconBuilder>(DefaultIconRenderer()) {

    var iconProperty: Property<Tile> = Tile.empty().toProperty()
    var iconTile: Tile
        get() = iconProperty.value
        set(value) {
            iconProperty.value = value
        }

    fun withIcon(icon: Tile) = also {
        this.iconTile = icon
    }

    fun withIconProperty(iconProperty: Property<Tile>) = also {
        this.iconProperty = iconProperty
    }

    override fun build(): Icon {
        return DefaultIcon(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            iconProperty = iconProperty
        ).attachListeners()
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withIcon(iconTile)

    companion object {

        @JvmStatic
        fun newBuilder() = IconBuilder()
    }
}
