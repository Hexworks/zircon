package org.hexworks.zircon.internal.application

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.internal.listeners.ZirconInputListener

class LibgdxGame(private val appConfig: AppConfig) : Game() {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val tileset = appConfig.defaultTileset

    private lateinit var batch: SpriteBatch

    lateinit var libgdxApplication: LibgdxApplication

    override fun create() {
        logger.info("Creating LibgdxGame...")

        batch = SpriteBatch()
        batch.enableBlending()

        libgdxApplication = LibgdxApplications.buildApplication(appConfig)
        libgdxApplication.start()

        Gdx.input.inputProcessor = ZirconInputListener(tileset.width, tileset.height)
    }

    override fun render() {
        super.render()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        libgdxApplication.render()
    }

    override fun dispose() {
        batch.dispose()
    }
}
