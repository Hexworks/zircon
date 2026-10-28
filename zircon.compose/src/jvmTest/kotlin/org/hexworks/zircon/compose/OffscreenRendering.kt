package org.hexworks.zircon.compose

import androidx.compose.ui.graphics.asComposeCanvas
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.compose.shader.AtlasInfo
import org.hexworks.zircon.compose.shader.TileShader
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Surface
import java.io.File

/**
 * Renders a grid the way [TileGridView] does, but into a raster surface: one tick of the
 * renderer, then the shader passes. Returns the PNG bytes.
 */
internal fun renderToPng(tileGrid: TileGrid, ticks: Int = 1): ByteArray {
    ComposeResourceLoader.install()
    val renderer = ComposeRenderer(tileGrid)
    val frame = FrameData(tileGrid.size.width, tileGrid.size.height)
    val shader = TileShader(tileGrid.tileset.width, tileGrid.tileset.height)
    try {
        runBlocking {
            renderer.registry.loadTilesetFrom(tileGrid.tileset)
            renderer.registry.loadPending()
            repeat(ticks) {
                renderer.render(frame)
                renderer.registry.loadPending()
            }
        }
        val surface = Surface.makeRasterN32Premul(tileGrid.widthInPixels, tileGrid.heightInPixels)
        val atlases = renderer.registry.tilesets
            .filter { it.isLoaded }
            .associate { it.atlasId to AtlasInfo(it.atlas!!, it.atlasCellWidth, it.atlasCellHeight) }
        shader.draw(surface.canvas.asComposeCanvas(), frame, atlases)
        return checkNotNull(surface.makeImageSnapshot().encodeToData(EncodedImageFormat.PNG)).bytes
    } finally {
        shader.close()
        renderer.close()
    }
}

/**
 * Compares [png] with the golden file called [name]. A missing golden file is written and
 * the test passes, so new goldens are reviewed through git. Set `UPDATE_GOLDENS=1` to
 * overwrite. The actual output always lands in `build/golden/` for inspection.
 */
internal fun assertMatchesGolden(name: String, png: ByteArray) {
    val actualDir = File("build/golden").apply { mkdirs() }
    File(actualDir, "$name.png").writeBytes(png)
    val golden = File("src/jvmTest/resources/golden/$name.png")
    if (!golden.exists() || System.getenv("UPDATE_GOLDENS") == "1") {
        golden.parentFile.mkdirs()
        golden.writeBytes(png)
        return
    }
    check(golden.readBytes().contentEquals(png)) {
        "Rendering of '$name' differs from the golden image; compare build/golden/$name.png with ${golden.path}"
    }
}
