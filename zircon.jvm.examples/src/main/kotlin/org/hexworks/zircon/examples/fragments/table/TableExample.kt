package org.hexworks.zircon.examples.fragments.table

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.screen.Screen

/**
 * This example shows the usage of the table fragment.
 */
object TableExample {

    @JvmStatic
    fun main() {
//        val tableFragment: Table<Person> = buildTable()

//        val selectionPanel = buildPanel(tableFragment)

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
            .withSize(Size.create(60, 40))//tableFragment.size)
            .withBorderless(true)
            .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
            .build())

        val screen = Screen.create(tileGrid)
        screen.theme = ColorThemes.adriftInDreams()

//        screen.addFragment(tableFragment)
    }

//    private fun buildTable(): Table<Person> =
        // TODO: Use Fragments.table()
//        DefaultTable(
//            50.randomPersons(),
//            listOf(
//                TableColumn("first name", 14)
//            )
//        )
}