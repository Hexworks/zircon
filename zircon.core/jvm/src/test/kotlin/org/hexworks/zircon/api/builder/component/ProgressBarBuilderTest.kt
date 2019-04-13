package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.ToggleButton
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class ProgressBarBuilderTest : ComponentBuilderTest<ProgressBar, ProgressBarBuilder>() {

    override lateinit var target: ProgressBarBuilder

    @Before
    override fun setUp() {
        target = ProgressBarBuilder.newBuilder()
    }

    @Test(expected = UnsupportedOperationException::class)
    override fun shouldProperlyApplyTitle() {
        target.withTitle(TITLE_FOO)
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun shouldRaiseAnExceptionIfInvalidRange() {
        target.withRange(0)
    }
}

