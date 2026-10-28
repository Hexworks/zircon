package org.hexworks.zircon.api.util.markovchain

import org.hexworks.zircon.internal.util.markovchain.DefaultMarkovChain

/**
 * Represents a markov chain.
 */
//! TODO: create a builder dsl that allows for the easy construction of markov chains
public interface MarkovChain<T : Any> {

    /**
     * Returns the first (initial) node in this [MarkovChain].
     */
    public fun first(): MarkovChainNode<T>

    /**
     * Returns the current node in this [MarkovChain].
     */
    public fun current(): MarkovChainNode<T>

    /**
     * Calculates and returns the next state of this [MarkovChain].
     * Note that after calling `next` the `current` state of this
     * markov chain will be set to the value returned by `next`.
     */
    public fun next(): MarkovChainNode<T>

    /**
     * Resets this [MarkovChain] to its initial state (the value
     * returned by `first`) and returns it.
     */
    public fun reset(): MarkovChainNode<T>

    public companion object {

        public fun <T : Any> create(initialNode: MarkovChainNode<T>): MarkovChain<T> = DefaultMarkovChain(initialNode)
    }

}
