package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig

data class CommonComponentProperties<T : Component>(
        var colorTheme: ColorTheme = ColorThemes.default(),
        var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        var alignmentStrategy: AlignmentStrategy = ComponentAlignments.positionalAlignment(0, 0),
        var decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
        var componentRenderer: ComponentRenderer<out T> = NoOpComponentRenderer())
