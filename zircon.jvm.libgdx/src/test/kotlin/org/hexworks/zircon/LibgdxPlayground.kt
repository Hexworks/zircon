package org.hexworks.zircon

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.application.LibgdxApplication
import org.hexworks.zircon.internal.listeners.ZirconInputListener
import org.hexworks.zircon.internal.util.fromBottom
import org.hexworks.zircon.internal.util.fromRight
import org.hexworks.zircon.internal.util.gridFillByScreenSize


object LibgdxPlayground : Game() {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var zirconApplication: LibgdxApplication

    private const val screenWidth = 1000
    private const val screenHeight = 600

    override fun create() {
        logger.info("Creating LibgdxPlayground...")
        zirconApplication = LibgdxApplications.buildApplication(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(gridFillByScreenSize(screenWidth = screenWidth, screenHeight = screenHeight, tileWidth = TILESET.width, tileHeight = TILESET.height))
                .build())
        zirconApplication.start()

        val screen = ScreenBuilder.createScreenFor(zirconApplication.tileGrid)

        val panel = Components.panel()
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
        panel.addComponent(button)

        screen.applyColorTheme(theme)
        screen.display()

        Gdx.input.inputProcessor = ZirconInputListener(TILESET.width, TILESET.height)
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

    private val TILESET = BuiltInCP437TilesetResource.WANDERLUST_16X16
    private val theme = ColorThemes.solarizedLightOrange()

    @JvmStatic
    fun main(args: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Zircon Playground"
        config.width = 1000
        config.height = 600
        config.foregroundFPS = 60
        config.useGL30 = true
        LwjglApplication(LibgdxPlayground, config)
    }
}
