package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig

data class CommonComponentProperties<T : Component>(
        var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        var position: Position = Position.zero(),
        var size: Size = Size.unknown(),
        var boxType: BoxType = BoxType.SINGLE,
        var wrapWithBox: Boolean = false,
        var wrapWithShadow: Boolean = false,
        var title: String = "",
        var decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
        var componentRenderer: ComponentRenderer<T> = NoOpComponentRenderer()) {

    fun hasTitle() = title.isNotBlank()
}
