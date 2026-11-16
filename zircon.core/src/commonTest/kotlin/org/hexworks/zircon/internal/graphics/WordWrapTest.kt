package org.hexworks.zircon.internal.graphics

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import kotlin.test.Test

class WordWrapTest {

    @Test
    fun a_word_that_does_not_fit_moves_whole_to_the_next_line() {
        wrapWords("a test thghty", 5) shouldBe listOf("a", "test", "thght", "y")
    }

    @Test
    fun the_separator_before_a_wrapped_word_is_dropped() {
        // the old tile layout kept the space, so "cd" landed on a third row
        wrapWords("ab cd", 2) shouldBe listOf("ab", "cd")
    }

    @Test
    fun separators_inside_a_line_are_kept() {
        wrapWords("a  b", 4) shouldBe listOf("a  b")
        wrapWords("  ab", 4) shouldBe listOf("  ab")
    }

    @Test
    fun a_word_longer_than_the_width_starts_on_a_fresh_line_and_is_broken() {
        wrapWords("a thghty", 5) shouldBe listOf("a", "thght", "y")
        wrapWords("atest", 4) shouldBe listOf("ates", "t")
    }

    @Test
    fun a_newline_starts_a_new_line() {
        wrapWords("ab\ncd ef", 5) shouldBe listOf("ab", "cd ef")
        wrapWords("ab\n", 5) shouldBe listOf("ab", "")
    }

    @Test
    fun lines_for_is_at_least_one() {
        linesFor("", 5) shouldBe 1
        linesFor("abc", 0) shouldBe 1
    }

    @Test
    fun the_drawn_rows_match_lines_for() {
        val text = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quas, eos!"
        for (width in 1..20) {
            val rows = linesFor(text, width)
            val drawn = characterTileString {
                +text
                textWrap = WORD_WRAP
                size = Size(width, rows + 5)
            }.tiles.keys.maxOf { it.y } + 1
            drawn shouldBe rows
        }
    }
}
