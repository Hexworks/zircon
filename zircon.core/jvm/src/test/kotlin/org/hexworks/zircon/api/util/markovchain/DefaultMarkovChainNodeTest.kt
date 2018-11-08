package org.hexworks.zircon.api.util.markovchain

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.util.Random
import org.junit.Test

class DefaultMarkovChainNodeTest {

    private val a = DefaultMarkovChainNode("A")
    private val b = DefaultMarkovChainNode("B")
    private val c = DefaultMarkovChainNode("C")

    class RandomStub(private val nextIntWithBound: Int = 0) : Random {

        override fun nextInt(bound: Int) = nextIntWithBound

        override fun nextInt() = 0
    }

    @Test
    fun shouldBeAbleToAddProbabilitiesEqualTo1() {
        a.addNext(.2, b)
        a.addNext(.8, c)
    }

    @Test
    fun shouldReturnFirstNextWhenRandomIsInRange() {
        val a = DefaultMarkovChainNode("A", RandomStub(4999))
        val b = DefaultMarkovChainNode("B")

        a.addNext(.5, b)
        val results = mutableSetOf<MarkovChainNode<String>>()
        (0..1000).forEach {
            results.add(a.next())
        }

        assertThat(results).hasSize(1)
        assertThat(results.first()).isSameAs(b)
    }

    @Test
    fun shouldReturnSecondNextWhenRandomIsInRange() {
        val a = DefaultMarkovChainNode("A", RandomStub(7999))
        val b = DefaultMarkovChainNode("B")
        val c = DefaultMarkovChainNode("C")


        a.addNext(.5, b)
        a.addNext(.3, c)
        val results = mutableSetOf<MarkovChainNode<String>>()
        (0..1000).forEach {
            results.add(a.next())
        }

        assertThat(results).hasSize(1)
        assertThat(results.first()).isSameAs(c)
    }

    @Test
    fun shouldReturnThisWhenRandomIsOutOfRange() {
        val a = DefaultMarkovChainNode("A", RandomStub(5001))
        val b = DefaultMarkovChainNode("B")

        a.addNext(.5, b)
        val results = mutableSetOf<MarkovChainNode<String>>()
        (0..1000).forEach {
            results.add(a.next())
        }

        assertThat(results).hasSize(1)
        assertThat(results.first()).isSameAs(a)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToAddWrongProbability() {
        a.addNext(1.1, b)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToAddProbabilityWhichHasGreaterThan1Total() {
        a.addNext(.5, b)
        a.addNext(.51, c)
    }
}
