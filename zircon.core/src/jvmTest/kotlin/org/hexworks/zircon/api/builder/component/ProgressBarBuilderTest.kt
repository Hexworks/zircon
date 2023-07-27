package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.ProgressBar
import org.junit.Before
import org.junit.Test

class ProgressBarBuilderTest : JvmComponentBuilderTest<ProgressBar>() {

    override lateinit var target: ProgressBarBuilder

    @Before
    override fun setUp() {
        target = ProgressBarBuilder()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldRaiseAnExceptionIfInvalidRange() {
        target.range = 0
    }

    @Test
    fun shouldProperlySetDefaultContentWidth() {
        target.range = 10
        target.numberOfSteps = 10
        val progressBar = target.build()
        assertThat(progressBar.contentSize.width)
            .isEqualTo(10)
    }
}

