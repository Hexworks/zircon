package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.data.Size
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.UnsupportedOperationException

class TextBoxBuilderTest : ComponentBuilderTest<TextBox, TextBoxBuilder>() {

    override lateinit var target: TextBoxBuilder

    @Before
    override fun setUp() {
        target = TextBoxBuilder.newBuilder()
    }

    @Test(expected = UnsupportedOperationException::class)
    override fun shouldProperlySetSize() {
        super.shouldProperlySetSize()
    }

    @Test
    fun shouldProperlySizeProvidedParagraphBuilder() {
        val textBox = target.withContentWidth(20)
                .addParagraph(ParagraphBuilder.newBuilder()
                        .withText("This is a test paragraph"),
                        true)
                .build()

        assertThat(textBox.size)
                .isEqualTo(Size.create(20, 3))


    }
}
