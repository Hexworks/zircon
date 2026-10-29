package org.hexworks.zircon.terminal

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.loadCP437TilesetResource
import org.hexworks.zircon.api.resource.ResourceType
import kotlin.test.Test

class TerminalSessionTest {

    private fun grid() = tileGrid {
        size = Size(4, 2)
        tileset = loadCP437TilesetResource(8, 8, "x.png", ResourceType.PROJECT)
    }

    private fun session(tty: FakeTty, grid: org.hexworks.zircon.api.grid.TileGrid = grid()) =
        TerminalSession(grid, targetFps = 1000, quitKeys = setOf(CTRL_C), beforeFrame = {}, tty = tty)

    @Test
    fun failsFastWithoutTheKittyProtocolAndRestoresTheTerminal() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?62c")))
        val error = shouldThrow<IllegalStateException> { session(tty).run() }
        error.message!! shouldContain "kitty keyboard protocol"
        tty.closed shouldBe true
        tty.outputText shouldStartWith "␛[?u␛[c"
    }

    @Test
    fun entersModesRendersAndLeavesOnCtrlC() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c"), bytes("\u001b[99;5u")))
        val session = session(tty)
        session.run()
        tty.opened shouldBe true
        tty.closed shouldBe true
        session.framesRendered shouldBe 1L
        val text = tty.outputText
        text shouldContain "␛[?1049h␛[?25l␛[>31u␛[?1003h␛[?1006h"
        text shouldContain "␛[2J"
        text shouldEndWith "␛[?1003l␛[?1006l␛[<u␛[0m␛[?25h␛[?1049l"
    }

    @Test
    fun stopsWhenTheGridIsClosed() {
        val grid = grid()
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c")), idleReads = 100)
        val session = TerminalSession(grid, 1000, emptySet(), beforeFrame = { grid.close() }, tty = tty)
        session.run()
        session.framesRendered shouldBe 1L
    }

    @Test
    fun stopsWhenTheInputEnds() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c")), idleReads = 2)
        session(tty).run()
        tty.closed shouldBe true
    }
}
