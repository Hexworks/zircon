package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size

object TrueTypeFontExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.amigaOs()

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(20, 10))
                .withDefaultTileset(TrueTypeFontResources.ibmBios(60))
                .build())

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .withText("This is written with a true type font...")
                .withSize(tileGrid.size)
                .withForegroundColor(theme.primaryForegroundColor)
                .withBackgroundColor(theme.primaryBackgroundColor)
                .build())


    }

}
