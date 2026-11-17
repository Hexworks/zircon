package org.hexworks.zircon.terminal

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.cobalt.logging.api.LogSink
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.component.theme.SystemTheme
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.loadCP437TilesetResource
import org.hexworks.zircon.api.resource.ResourceType
import kotlin.test.Test

class TerminalSessionTest {

    private fun grid() = tileGrid {
        size = Size(4, 2)
        tileset = loadCP437TilesetResource(8, 8, "x.png", ResourceType.PROJECT, title = "x")
    }

    private fun session(
        tty: FakeTty,
        grid: org.hexworks.zircon.api.grid.TileGrid = grid(),
        onTerminalColors: (TerminalColors) -> Unit = {},
        requireKittyKeyboard: Boolean = false,
    ) = TerminalSession(
        grid,
        targetFps = 1000,
        quitKeys = setOf(CTRL_C),
        beforeFrame = {},
        tty = tty,
        onTerminalColors = onTerminalColors,
        requireKittyKeyboard = requireKittyKeyboard,
    )

    private fun colorReplies(background: String, foreground: String = "cdd6f4"): String =
        "\u001b]10;rgb:${foreground.substring(0, 2)}/${foreground.substring(2, 4)}/${foreground.substring(4)}\u001b\\" +
            "\u001b]11;rgb:${background.substring(0, 2)}/${background.substring(2, 4)}/${background.substring(4)}\u001b\\" +
            "\u001b]4;4;rgb:89/b4/fa\u001b\\"

    @Test
    fun runsOnLegacyKeysWithoutTheKittyProtocolAndWarnsOnceTheTerminalIsRestored() {
        // DA1 without a flags reply (tmux, xterm), then a legacy Ctrl+C byte.
        val tty = FakeTty(reads = listOf(bytes("\u001b[?62c"), bytes("\u0003")))
        val session = session(tty)
        session.run()
        session.hasKittyKeyboard shouldBe false
        session.framesRendered shouldBe 1L
        tty.closed shouldBe true
        tty.outputText shouldContain "␛[?u␛[c"
        tty.outputText shouldContain "␛[?1049h"
        tty.errorText shouldContain "[WARN]"
        tty.errorText shouldContain "legacy keys"
    }

    @Test
    fun failsFastWithoutTheKittyProtocolWhenTheApplicationRequiresIt() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?62c")))
        val error = shouldThrow<IllegalStateException> { session(tty, requireKittyKeyboard = true).run() }
        error.message!! shouldContain "kitty keyboard protocol"
        tty.closed shouldBe true
        tty.outputText shouldContain "␛[?u␛[c"
        tty.outputText shouldNotContain "␛[?1049h"
    }

    @Test
    fun aTerminalWithTheKittyProtocolGetsNoWarning() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c"), bytes("\u001b[99;5u")))
        val session = session(tty)
        session.run()
        session.hasKittyKeyboard shouldBe true
        tty.errorText shouldBe ""
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
    fun holdsLogsBackUntilTheTerminalIsRestored() {
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c")), idleReads = 100)
        val grid = grid()
        val session = TerminalSession(grid, 1000, emptySet(), beforeFrame = {
            LoggerFactory.getLogger("test").error { "boom" }
            tty.errorText shouldBe ""
            grid.close()
        }, tty = tty)
        session.run()
        tty.outputText shouldNotContain "boom"
        tty.errorText shouldBe "[ERROR] test: boom\n"
        LoggerFactory.sink shouldBe LoggerFactory.defaultSink
    }

    @Test
    fun leavesAnInstalledLogSinkAlone() {
        val own = LogSink { _, _, _, _ -> }
        LoggerFactory.sink = own
        try {
            val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c")), idleReads = 2)
            session(tty).run()
            LoggerFactory.sink shouldBe own
            tty.errorText shouldBe ""
        } finally {
            LoggerFactory.sink = LoggerFactory.defaultSink
        }
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

    @Test
    fun readsTheTerminalColorsAtStartupAndAgainWhenTheSchemeChanges() {
        val seen = ArrayList<TerminalColors>()
        val tty = FakeTty(
            reads = listOf(
                bytes(colorReplies("1e1e2e") + "\u001b[?31u\u001b[?62c"),
                bytes("\u001b[?997;2n"),
                bytes(colorReplies("ffffff", "202020") + "\u001b[?62c"),
                bytes("\u001b[99;5u"),
            )
        )
        val session = session(tty, onTerminalColors = { seen += it })
        session.run()

        seen.map { it.background } shouldBe listOf(
            org.hexworks.zircon.api.color.Color.fromString("#1e1e2e"),
            org.hexworks.zircon.api.color.Color.fromString("#ffffff"),
        )
        seen[0].isDark shouldBe true
        seen[1].isDark shouldBe false
        seen[0].ansi[4] shouldBe org.hexworks.zircon.api.color.Color.fromString("#89b4fa")
        seen[0].ansi[1] shouldBe TerminalColors.DEFAULT_ANSI[1]
        session.terminalColors shouldBe seen[1]
        // The terminal's colors are the system palette, its background the system mode.
        SystemTheme.palette?.name shouldBe "Terminal"
        SystemTheme.palette?.isDark shouldBe false
        SystemTheme.mode shouldBe ThemeMode.LIGHT
        SystemTheme.reset()
        val text = tty.outputText
        text shouldStartWith "␛]10;?␛\\␛]11;?␛\\␛]4;0;?"
        text shouldContain "␛[?2031h"
        text shouldEndWith "␛[?2031l␛[?1003l␛[?1006l␛[<u␛[0m␛[?25h␛[?1049l"
        // The notification made the session ask for everything again.
        Regex("␛]10;\\?").findAll(text).count() shouldBe 2
    }

    @Test
    fun aTerminalThatDoesNotAnswerTheColorQueriesReportsNothing() {
        val seen = ArrayList<TerminalColors>()
        val tty = FakeTty(reads = listOf(bytes("\u001b[?31u\u001b[?62c"), bytes("\u001b[99;5u")))
        val session = session(tty, onTerminalColors = { seen += it })
        session.run()
        seen shouldBe emptyList()
        session.terminalColors shouldBe null
    }
}
