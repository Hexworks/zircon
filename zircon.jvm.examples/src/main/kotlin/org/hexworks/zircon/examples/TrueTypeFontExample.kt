package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource

object TrueTypeFontExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.amigaOs()

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(20, 10))
                .withDefaultTileset(BuiltInTrueTypeFontResource.IBM_BIOS.toTilesetResource(60))
                .build())

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .withText("This is written with a true type font...")
                .withForegroundColor(theme.primaryForegroundColor)
                .withBackgroundColor(theme.primaryBackgroundColor)
                .build())


    }

}
