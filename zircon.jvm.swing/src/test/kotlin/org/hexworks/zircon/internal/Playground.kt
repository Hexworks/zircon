package org.hexworks.zircon.internal

import ch.qos.logback.classic.Level
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.component.ComponentAlignment.*
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

        val screen = ScreenBuilder.createScreenFor(tileGrid)

        Components.textBox()
                .addParagraph("Click the button below to start!")
                .withAlignmentWithin(screen, TOP_LEFT)


    }

}
