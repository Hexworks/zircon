package org.hexworks.zircon

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.internal.application.LibgdxApplication
import org.hexworks.zircon.internal.listeners.ZirconInputListener


object LibgdxPlayground : Game() {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var zirconApplication: LibgdxApplication

    private const val screenWidth = 800
    private const val screenHeight = 600

    override fun create() {
        logger.info("Creating LibgdxPlayground...")
        zirconApplication = LibgdxApplications.buildApplication(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(Sizes.create(
                        screenWidth / TILESET.width,
                        screenHeight / TILESET.height))
                .build())
        zirconApplication.start()

        val screen = ScreenBuilder.createScreenFor(zirconApplication.tileGrid)

        /*val panel = Components.panel()
                .wrapWithBox(true)
                .wrapWithShadow(true)
                .withSize(Sizes.create(30, 28))
                .withPosition(Positions.create(0, 0))
                .withTitle("Toolbar buttons on panel")
                .build()
        screen.addComponent(panel)

        val unselectedToggleButton = Components.toggleButton()
                .withText("Toggle me")
                .wrapSides(true)
                .withPosition(Positions.create(1, 3))
        val selectedToggleButton = Components.toggleButton()
                .withText("Boxed Toggle Button")
                .withIsSelected(true)
                .wrapWithBox(true)
                .wrapSides(false)
                .withPosition(Positions.create(1, 5))
        val label = Components.label()
                .withText("I'm on the right!")
                .withPosition(Position.create(20, 9).fromRight(panel))
        val button = Components.button()
                .withText("Bottom text")
                .withPosition(Position.create(1, 4).fromBottom(panel))

        panel.addComponent(unselectedToggleButton)
        panel.addComponent(selectedToggleButton)
        panel.addComponent(label)
        panel.addComponent(button)*/

        screen.addComponent(Components.icon()
                .withAlignment(positionalAlignment(2, 2))
                .withIcon(Tiles.newBuilder()
                        .withName("Plate mail")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile())
        )
        screen.addComponent(Components.label()
                .withText("Label with icon")
                .withAlignment(positionalAlignment(2, 1)))


        screen.applyColorTheme(theme)
        screen.display()

        Gdx.input.inputProcessor = ZirconInputListener(
                fontWidth = TILESET.width,
                fontHeight = TILESET.height,
                tileGrid = zirconApplication.tileGrid)
    }

    override fun render() {
        super.render()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        zirconApplication.render()
    }

    override fun dispose() {
        zirconApplication.dispose()
    }

    private val TILESET = CP437TilesetResources.rexPaint16x16()
    private val theme = ColorThemes.arc()

    @JvmStatic
    fun main(args: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Zircon Playground"
        config.width = screenWidth
        config.height = screenHeight
        config.foregroundFPS = 60
        config.useGL30 = true
        config.fullscreen = false
        LwjglApplication(LibgdxPlayground, config)
    }
}
