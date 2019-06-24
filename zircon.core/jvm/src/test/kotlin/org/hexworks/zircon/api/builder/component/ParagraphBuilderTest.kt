package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.junit.Before

class ParagraphBuilderTest : ComponentBuilderTest<Paragraph, ParagraphBuilder>() {

    override lateinit var target: ParagraphBuilder

    @Before
    override fun setUp() {
        target = ParagraphBuilder.newBuilder()
    }
}
