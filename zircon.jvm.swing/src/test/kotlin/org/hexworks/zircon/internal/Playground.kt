package org.hexworks.zircon.internal

import ch.qos.logback.classic.Level
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object Playground {

    private val SIZE = Sizes.create(50, 30)
    private val TILESET = BuiltInCP437TilesetResource.TAFFER_20X20

    @JvmStatic
    fun main(args: Array<String>) {

        val root = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
        root.level = Level.DEBUG

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
