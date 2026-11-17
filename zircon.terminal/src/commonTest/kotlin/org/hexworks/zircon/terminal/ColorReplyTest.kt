package org.hexworks.zircon.terminal

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.terminal.input.InputParser
import kotlin.test.Test

class ColorReplyTest {

    private val replies = ArrayList<String>()
    private val parser = InputParser { column, row -> Position(column, row) }.apply {
        replyListener = object : InputParser.ReplyListener {
            override fun onColor(code: Int, index: Int, color: Color) {
                replies += "$code:$index:${color.red},${color.green},${color.blue}"
            }

            override fun onColorScheme(dark: Boolean) {
                replies += if (dark) "dark" else "light"
            }
        }
    }

    @Test
    fun oscColorRepliesReachTheListenerAndProduceNoEvents() {
        parser.feed(bytes("\u001b]11;rgb:1e1e/2e2e/3e3e\u001b\\\u001b]10;rgb:cd/d6/f4\u0007\u001b]4;4;rgb:89/b4/fa\u001b\\")).shouldBeEmpty()
        replies shouldContainExactly listOf("11:-1:30,46,62", "10:-1:205,214,244", "4:4:137,180,250")
    }

    @Test
    fun aSplitReplyWaitsForItsTerminator() {
        parser.feed(bytes("\u001b]11;rgb:ffff/ff")).shouldBeEmpty()
        replies.shouldBeEmpty()
        parser.feed(bytes("ff/0000\u001b\\")).shouldBeEmpty()
        replies shouldContainExactly listOf("11:-1:255,255,0")
    }

    @Test
    fun theColorSchemeReportIsNotAKey() {
        parser.feed(bytes("\u001b[?997;2n\u001b[?997;1n")).shouldBeEmpty()
        replies shouldContainExactly listOf("light", "dark")
    }

    @Test
    fun colorSpecsCoverTheXorgFormsAndHex() {
        InputParser.parseColorSpec("rgb:ff/80/00") shouldBe Color.create(255, 128, 0)
        InputParser.parseColorSpec("rgb:ffff/8080/0000") shouldBe Color.create(255, 128, 0)
        InputParser.parseColorSpec("rgb:f/8/0") shouldBe Color.create(255, 136, 0)
        InputParser.parseColorSpec("#ff8000") shouldBe Color.create(255, 128, 0)
        InputParser.parseColorSpec("rgbi:1/0/0") shouldBe null
    }
}
