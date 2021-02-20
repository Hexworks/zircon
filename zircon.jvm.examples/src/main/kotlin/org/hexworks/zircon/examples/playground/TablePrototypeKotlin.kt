package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

object TablePrototypeKotlin {

    data class Model(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val job: String
    )


    @JvmStatic
    fun main(args: Array<String>) {

        val fields = listOf(
            TableField("#", 5, Model::id),
            TableField("First Name", 15, Model::firstName),
            TableField("Last Name", 15, Model::lastName),
            TableField("Job", 15, Model::job)
        )

        val models = listOf(
            Model(1, "Sam", "Lawrey", "Stoneworker"),
            Model(2, "Jane", "Fischer", "Woodcutter"),
            Model(3, "John", "Smith", "Mason"),
            Model(4, "Steve", "Thrush", "Farmer"),
            Model(5, "Bob", "Wagner", "Musician"),
            Model(5, "Amanda", "Flair", "Brewer")
        )

        val table = TableOld(
            fields = fields,
            models = models
        )

        val screen = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                .build()
        ).toScreen()

        screen.addComponent(Components
            .panel()
            .withComponentRenderer(NoOpComponentRenderer())
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .withSize(table.root.size + Size.create(3, 3))
            .withDecorations(box(title = "Table"), shadow())
            .build().apply { addFragment(table) })

        screen.display()
        screen.theme = ColorThemes.gamebookers()
    }
}

