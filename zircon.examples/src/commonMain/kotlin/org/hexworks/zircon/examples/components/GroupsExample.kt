package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.component.theme.TokyoNightStyle
import org.hexworks.zircon.api.component.theme.KanagawaVariant
import org.hexworks.zircon.api.component.theme.CatppuccinFlavor
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.group
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.relativeToRightOf
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Two panels with their own themes and tilesets; adding them to a group unifies both. */
public val groupsExample: Example = Example("Groups") { context ->
    val root = panel {
        preferredSize = context.contentSize
        componentRenderer = NoOpComponentRenderer()
    }
    val leftPanel = panel {
        preferredSize = Size(15, 10)
        position = Position(6, 5)
        decorations { +box(title = "Left Panel") }
        componentPalette = Palettes.catppuccin(CatppuccinFlavor.LATTE)
        tileset = CP437TilesetResources.aduDhabi16x16()
    }
    val rightPanel = panel {
        preferredSize = Size(15, 10)
        position = Position(18, 0).relativeToRightOf(leftPanel)
        decorations { +box(title = "Right Panel") }
        componentPalette = Palettes.kanagawa(KanagawaVariant.DRAGON)
        tileset = CP437TilesetResources.cooz16x16()
    }
    val group = group<Component> {
        tileset = CP437TilesetResources.rexPaint16x16()
    }
    val groupButton = button {
        +"Add Panels to Group"
        alignment = alignmentWithin(root, ComponentAlignment.BOTTOM_CENTER)
        componentPalette = Palettes.tokyoNight(TokyoNightStyle.STORM)
    }.apply {
        onActivated { group.addComponents(leftPanel, rightPanel) }
    }
    root.addComponent(leftPanel)
    root.addComponent(rightPanel)
    root.addComponent(groupButton)
    ExampleView(root)
}
