package org.hexworks.zircon.renderer.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer
import org.hexworks.zircon.renderer.compose.tileset.SkiaPixelBuffer

/**
 * Compose-based renderer for Zircon tile grids.
 *
 * This renderer uses Skia for high-performance CPU-based rendering
 * with a single GPU upload per frame.
 */
class ComposeRenderer(
    tileGrid: InternalTileGrid,
    tilesetLoader: TilesetLoader<ComposeContext>,
) : BaseRenderer<ComposeContext, ComposeApplication, Unit>(tileGrid, tilesetLoader) {

    private val config = tileGrid.config
    private val widthInPixels = tileGrid.widthInPixels
    private val heightInPixels = tileGrid.heightInPixels
    private val tileWidth = tileGrid.tileset.width
    private val tileHeight = tileGrid.tileset.height

    private val keyboardEventListener = KeyboardEventListener(tileGrid)
    private val mouseEventListener = MouseEventListener(tileGrid)

    // These are lazily initialized when the composable is first created
    private var pixelBuffer: SkiaPixelBuffer? = null
    private var composeContext: ComposeContext? = null

    override fun create() {
        // Initialization happens in the composable
    }

    override fun processInputEvents() {
        keyboardEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
        mouseEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
    }

    override fun prepareRender(context: ComposeContext) {
        // Clear the pixel buffer with a default background color
        context.clear(0xFF000000.toInt())
    }

    override fun doClose() {
        composeContext?.close()
        composeContext = null
        pixelBuffer = null
    }

    /**
     * Creates the rendering Composable for this renderer.
     */
    @Composable
    fun RenderingCanvas() {
        val focusRequester = remember { FocusRequester() }

        // Initialize rendering resources
        DisposableEffect(Unit) {
            pixelBuffer = SkiaPixelBuffer(widthInPixels, heightInPixels)
            composeContext = ComposeContext(
                pixelBuffer = pixelBuffer!!,
                tileWidth = tileWidth,
                tileHeight = tileHeight
            )

            onDispose {
                composeContext?.close()
                composeContext = null
                pixelBuffer = null
            }
        }

        // Request focus for keyboard input
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        // State for the rendered image
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        // Continuous rendering loop
        LaunchedEffect(Unit) {
            while (true) {
                withFrameNanos { _ ->
                    val ctx = composeContext
                    if (ctx != null) {
                        render(ctx)
                        imageBitmap = ctx.toImageBitmap()
                    }
                }
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .focusable()
                .onKeyEvent { event ->
                    keyboardEventListener.handleKeyEvent(event)
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            mouseEventListener.handlePointerEvent(event)
                        }
                    }
                }
        ) {
            imageBitmap?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    srcOffset = IntOffset.Zero,
                    srcSize = IntSize(widthInPixels, heightInPixels),
                    dstOffset = IntOffset.Zero,
                    dstSize = IntSize(widthInPixels, heightInPixels)
                )
            }
        }
    }
}
