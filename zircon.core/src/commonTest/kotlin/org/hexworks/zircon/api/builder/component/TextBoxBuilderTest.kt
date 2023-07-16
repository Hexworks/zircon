package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.component.TextBox
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

val BOX_WIDTH = 20

class TextBoxBuilderTest : ComponentBuilderTest<TextBox, TextBoxBuilder>() {

    override lateinit var target: TextBoxBuilder

    @BeforeTest
    override fun setUp() {
        target = TextBoxBuilder(BOX_WIDTH)
    }

    @Test
    override fun shouldProperlySetSize() {
        try {
            super.shouldProperlySetSize()
            error("should have thrown")
        } catch (e: Exception) {
            assertEquals(UnsupportedOperationException::class, e::class)
        }
    }

    @Test
    fun shouldProperlySizeProvidedParagraphBuilder() {

        val textBox = target.apply {
            blocks {
                +buildParagraph {
                    +"This is a test paragraph"
                }
            }
        }

        assertEquals(size {
            width = BOX_WIDTH
            height = 3
        }, textBox.size)
    }

    @Test
    fun shouldBeAbleToAddInlineElementsWithinBounds() {

        val textBox = target.apply {
            inline {
                +buildParagraph {
                    +"Some text"
                }
                +buildButton {
                    +"go button"
                }
            }
        }

        assertEquals(size {
            width = BOX_WIDTH
            height = 1
        }, textBox.size)
    }
}
