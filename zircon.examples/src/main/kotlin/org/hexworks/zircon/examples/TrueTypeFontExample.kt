package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.resource.BuiltInMonospaceFontResource

object TrueTypeFontExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(BuiltInMonospaceFontResource.TEST)
                .build())

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .text("This is written with a true type font...")
                .foregroundColor(ANSITileColor.YELLOW)
                .backgroundColor(ANSITileColor.BLUE)
                .build())


    }

}
