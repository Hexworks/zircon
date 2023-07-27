package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.junit.Before

class TextAreaBuilderTest : JvmComponentBuilderTest<TextArea>() {

    override lateinit var target: TextAreaBuilder

    @Before
    override fun setUp() {
        target = TextAreaBuilder()
    }
}
