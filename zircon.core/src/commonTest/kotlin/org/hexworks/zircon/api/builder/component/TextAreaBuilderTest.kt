package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import kotlin.test.BeforeTest

class TextAreaBuilderTest : JvmComponentBuilderTest<TextArea>() {

    override lateinit var target: TextAreaBuilder

    @BeforeTest
    override fun setUp() {
        target = TextAreaBuilder()
    }
}
