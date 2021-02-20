package org.hexworks.zircon.examples.fragments.table

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.fragment.impl.table.DefaultTable
import org.hexworks.zircon.internal.fragment.impl.table.TableColumn

/**
 * This example shows the usage of the table fragment.
 */
object TableExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val tableFragment: Table<Person> = buildTable()

//        val selectionPanel = buildPanel(tableFragment)

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withSize(tableFragment.size)
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withTitle("Table example")
                .build()
        )

        val screen = Screen.create(tileGrid)

        screen.addFragment(tableFragment)
        screen.theme = ColorThemes.amigaOs()
        screen.display()
    }

    private fun buildTable(): Table<Person> =
        // TODO: Use Fragments.table()
        DefaultTable(
            50.randomPersons(),
            listOf(
                TableColumn(
                    "first name",
                    14,
                    Person::firstName
                ) {
                    Components.label().withSize(14, 1).withText(it).build()
                }
            ),
            20
        )
}