package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.resource.BuiltInMonospaceFontResource

object TrueTypeFontExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.amigaOs()

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultSize(Sizes.create(20, 10))
                .defaultTileset(BuiltInMonospaceFontResource.IBM_BIOS.toTilesetResource(60))
                .build())

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .text("This is written with a true type font...")
                .foregroundColor(theme.primaryForegroundColor())
                .backgroundColor(theme.primaryBackgroundColor())
                .build())


    }

}
