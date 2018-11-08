package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.junit.Before

class TextAreaBuilderTest : ComponentBuilderTest<TextArea, TextAreaBuilder>() {

    override lateinit var target: TextAreaBuilder

    @Before
    override fun setUp() {
        target = TextAreaBuilder.newBuilder()
    }
}
