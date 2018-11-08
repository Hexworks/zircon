package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.Paragraph
import org.junit.Before
import org.junit.Test

class ParagraphBuilderTest : ComponentBuilderTest<Paragraph, ParagraphBuilder>() {

    override lateinit var target: ParagraphBuilder

    @Before
    override fun setUp() {
        target = ParagraphBuilder.newBuilder()
    }

    @Test
    override fun shouldProperlyApplyTitle() {
        target.withTitle(TITLE_FOO)

        assertThat(target.title).isEmpty()
    }
}
