package org.hexworks.zircon.api.util.markovchain


import org.hexworks.zircon.api.behavior.Identifiable
import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChainNode
import kotlin.random.Random

/**
 * Represents a markov chain node.
 */
// TODO: refactor this
public interface MarkovChainNode<T : Any> : Identifiable {

    /**
     * Calculates and returns the next state of this markov chain.
     */
    public fun next(): MarkovChainNode<T>

    /**
     * Returns the satellite data stored in this node or an exception if it is not present.
     */
    public fun data(): T

    /**
     * Returns the satellite data stored in this node or `null` if it is not present.
     */
    public fun dataOrNull(): T?

    /**
     * Adds a new [MarkovChainNode] and returns `this`.
     */
    public fun addNext(probability: Double, nextNode: MarkovChainNode<T>): MarkovChainNode<T>

    /**
     * Adds a new [MarkovChainNode] with the given `data` and returns `this`.
     */
    public fun setData(data: T): MarkovChainNode<T>

    public companion object {

        public fun <T : Any> create(): MarkovChainNode<T> = DefaultMarkovChainNode()

        public fun <T : Any> create(data: T): MarkovChainNode<T> = DefaultMarkovChainNode(data)

        public fun <T : Any> create(data: T, seed: Long): MarkovChainNode<T> = DefaultMarkovChainNode(data, Random(seed))
    }
}
