package org.hexworks.zircon.api.util.markovchain

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChain
import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChainNode
import org.junit.Before
import org.junit.Test

class DefaultMarkovChainTest {

    private val a = DefaultMarkovChainNode("A")
    private val b = DefaultMarkovChainNode("B")

    lateinit var target: DefaultMarkovChain<String>

    @Before
    fun setUp() {
        target = DefaultMarkovChain(a)
        a.addNext(1.0, b)
    }

    @Test
    fun shouldReturnNextWhenAskingForNext() {
        assertThat(target.next()).isSameAs(b)
    }

    @Test
    fun shouldSetCurrentToNextWhenNextIsCalled() {
        target.next()
        assertThat(target.current()).isSameAs(b)
    }

    @Test
    fun shouldResetWhenResetIsCalled() {
        target.next()
        target.reset()
        assertThat(target.current()).isSameAs(a)
    }

    @Test
    fun shouldReturnFirstWhenCurrentChanges() {
        target.next()
        assertThat(target.first()).isSameAs(a)
    }
}
