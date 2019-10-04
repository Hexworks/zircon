package org.hexworks.zircon.api.util.markovchain

/**
 * Represents a markov chain.
 */
interface MarkovChain<T : Any> {

    /**
     * Returns the first (initial) node in this [MarkovChain].
     */
    fun first(): MarkovChainNode<T>

    /**
     * Returns the current node in this [MarkovChain].
     */
    fun current(): MarkovChainNode<T>

    /**
     * Calculates and returns the next state of this [MarkovChain].
     * Note that after calling `next` the `current` state of this
     * markov chain will be set to the value returned by `next`.
     */
    fun next(): MarkovChainNode<T>

    /**
     * Resets this [MarkovChain] to its initial state (the value
     * returned by `first` and returns it.
     */
    fun reset(): MarkovChainNode<T>

    companion object {

        fun <T : Any> create(initialNode: MarkovChainNode<T>): MarkovChain<T> = DefaultMarkovChain(initialNode)
    }

}
