package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import kotlin.test.BeforeTest

class ParagraphBuilderTest : JvmComponentBuilderTest<Paragraph>() {

    override lateinit var target: ParagraphBuilder

    @BeforeTest
    override fun setUp() {
        target = ParagraphBuilder()
    }
}
