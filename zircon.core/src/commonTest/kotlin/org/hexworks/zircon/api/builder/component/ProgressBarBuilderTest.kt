package org.hexworks.zircon.api.builder.component

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.component.ProgressBar
import kotlin.test.BeforeTest
import kotlin.test.Test

class ProgressBarBuilderTest : JvmComponentBuilderTest<ProgressBar>() {

    override lateinit var target: ProgressBarBuilder

    @BeforeTest
    override fun setUp() {
        target = ProgressBarBuilder()
    }

    @Test
    fun shouldRaiseAnExceptionIfInvalidRange() {
        shouldThrow<IllegalArgumentException> {
            target.range = 0
        }
    }

    @Test
    fun shouldProperlySetDefaultContentWidth() {
        target.range = 10
        target.numberOfSteps = 10
        val progressBar = target.build()
        progressBar.contentSize.width shouldBe 10
    }
}
