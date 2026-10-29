package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.extensions.toScreen
import java.io.File
import kotlin.test.Test
import kotlin.test.fail

/**
 * Byte-level goldens of whole frames rendered through the real pipeline (grid → renderer →
 * encoder). ESC is stored as `␛` so the files are readable. `UPDATE_GOLDENS=1` rewrites
 * them; actuals always land in `build/golden`.
 */
class GoldenTest {

    @Test
    fun componentsFirstFrameAndDiff() {
        val grid = tileGrid {
            size = Size(24, 6)
            tileset = CP437TilesetResources.wanderlust16x16()
        }
        val screen = grid.toScreen()
        screen.theme = ColorThemes.arc()
        val box = vBox { preferredSize = Size(24, 6) }
        screen.addComponent(box)
        box.addComponent(header { +"Terminal" })
        val button = button { +"Press me" }
        box.addComponent(button)
        screen.display()

        val renderer = TerminalRenderer(grid)
        val frame = CellBuffer(24, 6)
        val encoder = AnsiEncoder(24, 6).apply { resize(30, 8) }
        val sink = ByteSink()

        renderer.render(frame)
        encoder.encode(frame, null, false, sink)
        assertMatchesGolden("components-first-frame", sink)

        grid.cursorPosition = Position(2, 3)
        grid.isCursorVisible = true
        button.text = "Pressed"
        sink.reset()
        renderer.render(frame)
        encoder.encode(frame, grid.cursorPosition, false, sink)
        assertMatchesGolden("components-diff-frame", sink)
        renderer.close()
    }

    private fun assertMatchesGolden(name: String, sink: ByteSink) {
        val actual = sink.toByteArray().decodeToString().readable()
        val goldenFile = File("src/jvmTest/resources/golden/$name.ansi")
        val actualFile = File("build/golden/$name.ansi").apply { parentFile.mkdirs() }
        actualFile.writeText(actual)
        if (System.getenv("UPDATE_GOLDENS") == "1" || !goldenFile.exists()) {
            goldenFile.parentFile.mkdirs()
            goldenFile.writeText(actual)
            return
        }
        val expected = goldenFile.readText()
        if (expected != actual) {
            fail("Golden '$name' differs; actual written to $actualFile. Rerun with UPDATE_GOLDENS=1 to accept.\nexpected: $expected\nactual:   $actual")
        }
    }
}
