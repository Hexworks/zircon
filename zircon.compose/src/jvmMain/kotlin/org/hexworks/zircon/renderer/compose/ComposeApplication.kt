package org.hexworks.zircon.renderer.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPlacement.Maximized
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowPosition.Absolute
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.renderer.Renderer

/**
 * Compose Desktop application for Zircon.
 *
 * This class manages the Compose Desktop window and integrates
 * the ComposeRenderer for tile grid rendering.
 */
class ComposeApplication(
    override val config: AppConfig,
    override val tileGrid: TileGrid,
    override val eventBus: EventBus,
    private val renderer: ComposeRenderer,
    override val eventScope: ZirconScope = ZirconScope(),
) : InternalApplication {

    init {
        tileGrid.asInternal().application = this
    }

    private val _closed = false.toProperty()

    override val closed: Boolean
        get() = _closed.value

    override val closedProperty: Property<Boolean>
        get() = _closed

    private var exitApplication: (() -> Unit)? = null

    /**
     * Starts the Compose Desktop application.
     * This method blocks until the window is closed.
     */
    override suspend fun start() {
        application {
            exitApplication = ::exitApplication

            Window(
                onCloseRequest = {
                    close()
                    exitApplication()
                },
                title = config.title,
                state = WindowState(
                    placement = Maximized,
                    position = Absolute(0.dp, 0.dp),
                    size = DpSize(
                        width = tileGrid.widthInPixels.dp,
                        height = tileGrid.heightInPixels.dp
                    )
                ),
                resizable = false,
                undecorated = true
            ) {
                ZirconContent()
            }
        }
    }

    @Composable
    private fun ZirconContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            renderer.RenderingCanvas()
        }
    }

    override fun beforeRender(listener: (RenderData) -> Unit) = renderer.beforeRender(listener)

    override fun afterRender(listener: (RenderData) -> Unit) = renderer.afterRender(listener)

    override fun asInternal(): InternalApplication = this

    override fun close() {
        _closed.value = true
        tileGrid.close()
        renderer.close()
        exitApplication?.invoke()
    }
}
