package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextBox
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
}
