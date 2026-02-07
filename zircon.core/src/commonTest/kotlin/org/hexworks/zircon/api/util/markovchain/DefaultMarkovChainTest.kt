package org.hexworks.zircon.api.util.markovchain

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChain
import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChainNode
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultMarkovChainTest {

    private val a = DefaultMarkovChainNode("A")
    private val b = DefaultMarkovChainNode("B")

    lateinit var target: DefaultMarkovChain<String>

    @BeforeTest
    fun setUp() {
        target = DefaultMarkovChain(a)
        a.addNext(1.0, b)
    }

    @Test
    fun shouldReturnNextWhenAskingForNext() {
        target.next() shouldBeSameInstanceAs b
    }

    @Test
    fun shouldSetCurrentToNextWhenNextIsCalled() {
        target.next()
        target.current() shouldBeSameInstanceAs b
    }

    @Test
    fun shouldResetWhenResetIsCalled() {
        target.next()
        target.reset()
        target.current() shouldBeSameInstanceAs a
    }

    @Test
    fun shouldReturnFirstWhenCurrentChanges() {
        target.next()
        target.first() shouldBeSameInstanceAs a
    }
}
