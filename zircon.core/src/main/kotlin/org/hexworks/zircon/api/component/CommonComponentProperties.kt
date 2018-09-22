package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.config.RuntimeConfig

data class CommonComponentProperties(
        var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        var position: Position = Position.defaultPosition(),
        var size: Size = Size.unknown(),
        var boxType: BoxType = BoxType.SINGLE,
        var wrapWithBox: Boolean = false,
        var wrapWithShadow: Boolean = false,
        var title: Maybe<String> = Maybe.empty(),
        var decorationRenderers: List<ComponentDecorationRenderer> = listOf())
