package org.hexworks.zircon.terminal

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.zircon.api.data.Position
import kotlin.test.Test

class AnsiEncoderTest {

    private val encoder = AnsiEncoder(4, 2).apply { resize(10, 4) }
    private val frame = CellBuffer(4, 2)
    private val sink = ByteSink(256)

    private fun encode(cursor: Position? = null, blinking: Boolean = false): String {
        sink.reset()
        encoder.encode(frame, cursor, blinking, sink)
        return sink.toByteArray().decodeToString().readable()
    }

    @Test
    fun firstFrameClearsAndDrawsEveryCellCentred() {
        encode() shouldBe "␛[?2026h␛[?25l␛[0m␛[2J" +
            "␛[2;4H␛[38;2;255;255;255;48;2;0;0;0m    " +
            "␛[3;4H    " +
            "␛[?2026l"
    }

    @Test
    fun unchangedFrameWritesNothing() {
        encode()
        encode() shouldBe ""
    }

    @Test
    fun onlyChangedCellsAreWrittenAndRunsShareColours() {
        encode()
        frame.codes[frame.index(1, 0)] = 'a'.code
        frame.foregrounds[frame.index(1, 0)] = 0xFF0000
        frame.codes[frame.index(2, 0)] = 'b'.code
        frame.foregrounds[frame.index(2, 0)] = 0xFF0000
        frame.codes[frame.index(0, 1)] = 'c'.code
        encode() shouldBe "␛[?2026h␛[2;5H␛[38;2;255;0;0mab␛[3;4H␛[38;2;255;255;255mc␛[?2026l"
    }

    @Test
    fun cursorIsPlacedShownAndHidden() {
        encode(cursor = Position(1, 1)) shouldContain "␛[3;5H␛[2 q␛[?25h␛[?2026l"
        encode(cursor = Position(1, 1)) shouldBe ""
        encode(cursor = Position(2, 1), blinking = true) shouldBe "␛[?2026h␛[3;6H␛[1 q␛[?2026l"
        encode(cursor = null) shouldBe "␛[?2026h␛[?25l␛[?2026l"
    }

    @Test
    fun tooSmallTerminalShowsTheBannerOnceAndRedrawsAfterResize() {
        encoder.resize(3, 3)
        encoder.tooSmall shouldBe true
        encode() shouldBe "␛[?2026h␛[?25l␛[0m␛[2J␛[1;1HTer␛[?2026l"
        encode() shouldBe ""
        encoder.resize(4, 2)
        encode() shouldStartWith "␛[?2026h␛[?25l␛[0m␛[2J␛[1;1H"
    }

    @Test
    fun cursorAfterTheLastTerminalColumnIsUnknown() {
        encoder.resize(4, 2)
        encode()
        frame.codes[frame.index(3, 0)] = 'x'.code
        frame.codes[frame.index(0, 1)] = 'y'.code
        encode() shouldBe "␛[?2026h␛[1;4Hx␛[2;1Hy␛[?2026l"
    }

    @Test
    fun terminalCellsMapIntoTheGridWithClamping() {
        encoder.toGridPosition(3, 1) shouldBe Position(0, 0)
        encoder.toGridPosition(6, 2) shouldBe Position(3, 1)
        encoder.toGridPosition(0, 0) shouldBe Position(0, 0)
        encoder.toGridPosition(9, 3) shouldBe Position(3, 1)
    }

    @Test
    fun wideAndControlCharactersBecomeOneCell() {
        TextTileset.cellCodeOf('漢') shouldBe TerminalCharacters.REPLACEMENT
        TextTileset.cellCodeOf('́') shouldBe TerminalCharacters.REPLACEMENT
        TextTileset.cellCodeOf('\u0007') shouldBe CellBuffer.SPACE
        TextTileset.cellCodeOf('\uD83D') shouldBe TerminalCharacters.REPLACEMENT
        TextTileset.cellCodeOf('║') shouldBe '║'.code
        TextTileset.cellCodeOf('') shouldBe 0xE0B0
    }
}
