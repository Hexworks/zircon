package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.Label
import org.junit.Before
import org.junit.Test

class LabelBuilderTest : ComponentBuilderTest<Label, LabelBuilder>() {

    override lateinit var target: LabelBuilder

    @Before
    override fun setUp() {
        target = LabelBuilder.newBuilder()
    }

    @Test
    override fun shouldProperlyApplyTitle() {
        target.withTitle(TITLE_FOO)

        assertThat(target.title).isEmpty()
    }
}

