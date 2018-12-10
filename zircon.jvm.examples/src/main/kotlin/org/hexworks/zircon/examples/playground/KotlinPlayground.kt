@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource

object KotlinPlayground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437TilesetResource.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(SIZE)
                .build())

        val screen0 = ScreenBuilder.createScreenFor(tileGrid)
        val screen1 = ScreenBuilder.createScreenFor(tileGrid)

        tileGrid.onInput {
            println(it)
            if(it.inputTypeIs(InputType.Enter)) {
                screen0.display()
            }
            if(it.inputTypeIs(InputType.ArrowRight)) {
                screen1.display()
            }
        }


    }

}
