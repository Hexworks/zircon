package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.api.resource.ColorThemeResource

object NonSquareTilesetExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .enableBetaFeatures()
                        .defaultSize(Sizes.create(50, 40))
                        .defaultTileset(BuiltInCP437TilesetResource.REX_PAINT_16X16)
                        .build())

        val screen = Screens.createScreenFor(tileGrid)

        val textBox = Components.textBox()
                .withPosition(Positions.create(2, 0))
                .contentWidth(20)
                .paragraph("This text is more readable because it does not use a square tileset.")
                .paragraph("This is a 8x16 tileset in fact...")
                .paragraph("It works fine with a Panel which uses a 16x16 tileset!")
                .withTileset(BuiltInTrueTypeFontResource.UBUNTU_MONO.toTilesetResource(16))
                .build()

        val panel = Components.panel()
                .withTitle("Multi-size test")
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withPosition(Positions.create(2, 2))
                .withSize(Sizes.create(40, 30))
                .build()

        panel.addComponent(textBox)

        screen.addComponent(panel)
        screen.applyColorTheme(ColorThemeResource.AMIGA_OS.getTheme())
        screen.display()


    }

}
