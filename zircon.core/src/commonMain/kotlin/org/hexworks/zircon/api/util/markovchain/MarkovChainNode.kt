package org.hexworks.zircon.api.util.markovchain

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.random.Random

/**
 * Represents a markov chain node.
 */
interface MarkovChainNode<T : Any> : Identifiable {

    /**
     * Calculates and returns the next state of this markov chain.
     */
    fun next(): MarkovChainNode<T>

    /**
     * Returns the satellite data stored in this node.
     */
    fun data(): Maybe<T>

    /**
     * Returns the satellite data stored in this node.
     */
    fun dataOrNull(): T?

    /**
     * Adds a new [MarkovChainNode] and returns `this`.
     */
    fun addNext(probability: Double, nextNode: MarkovChainNode<T>): MarkovChainNode<T>

    /**
     * Adds a new [MarkovChainNode] with the given `data` and returns `this`.
     */
    fun setData(data: T): MarkovChainNode<T>

    companion object {

        fun <T : Any> create(): MarkovChainNode<T> = DefaultMarkovChainNode()

        fun <T : Any> create(data: T): MarkovChainNode<T> = DefaultMarkovChainNode(data)

        fun <T : Any> create(data: T, seed: Long): MarkovChainNode<T> = DefaultMarkovChainNode(data, Random(seed))
    }
}
