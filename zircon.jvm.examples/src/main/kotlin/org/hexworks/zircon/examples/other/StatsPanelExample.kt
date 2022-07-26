package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.rexPaint16x16
import org.hexworks.zircon.api.ColorThemes.zenburnVanilla
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.Components.icon
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.textBox
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen.Companion.create

object StatsPanelExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(rexPaint16x16())
                .build()
        )
        val screen = create(tileGrid)
        val panel = vbox()
            .withDecorations(box(BoxType.SINGLE, "Sel Darkstrom"), shadow())
            .withPreferredSize(20, 22)
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .build()
        panel.addComponent(label().withPreferredSize(1, 1)) // spacer
        panel.addComponent(header().withText("Stats"))
        val attack = hbox()
            .withPreferredSize(panel.contentSize.width, 1)
            .build()
        attack.addComponent(
            icon().withIcon(
                Tile.newBuilder()
                    .withName("Short sword")
                    .withTileset(nethack16x16())
                    .buildGraphicalTile()
            ).build()
        )
        attack.addComponent(label().withText("Attack:5"))
        panel.addComponent(attack)
        val defense = hbox()
            .withPreferredSize(panel.contentSize.width, 1)
            .build()
        defense.addComponent(
            icon()
                .withIcon(
                    Tile.newBuilder()
                        .withName("Small shield")
                        .withTileset(nethack16x16())
                        .buildGraphicalTile()
                ).build()
        )
        defense.addComponent(
            label()
                .withText("Defense:4")
        )
        panel.addComponent(defense)
        panel.addComponent(label().withPreferredSize(1, 1))
        panel.addComponent(header().withText("Info"))
        panel.addComponent(
            textBox(panel.contentSize.width)
                .addParagraph("A short, sturdy creature fond of drink and industry.")
                .addParagraph("Abilities:")
                .addListItem("Drink")
                .addListItem("Dig")
                .addListItem("Sleep")
                .build()
        )
        screen.addComponent(panel)
        screen.theme = zenburnVanilla()
        screen.display()
    }
}