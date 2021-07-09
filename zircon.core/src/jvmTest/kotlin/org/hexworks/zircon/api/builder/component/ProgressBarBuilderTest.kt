package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.ProgressBar
import org.junit.Before
import org.junit.Test

class ProgressBarBuilderTest : ComponentBuilderTest<ProgressBar, ProgressBarBuilder>() {

    override lateinit var target: ProgressBarBuilder

    @Before
    override fun setUp() {
        target = ProgressBarBuilder.newBuilder()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldRaiseAnExceptionIfInvalidRange() {
        target.withRange(0)
    }

    @Test
    fun shouldProperlySetDefaultContentWidth() {
        target.withRange(10)
            .withNumberOfSteps(10)
        val progressBar = target.build()
        assertThat(progressBar.contentSize.width)
            .isEqualTo(10)
    }
}

