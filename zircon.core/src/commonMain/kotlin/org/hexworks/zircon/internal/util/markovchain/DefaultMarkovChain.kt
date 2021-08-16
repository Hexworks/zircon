package org.hexworks.zircon.internal.util.markovchain

import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode

class DefaultMarkovChain<T : Any> internal constructor(
    private val firstNode: MarkovChainNode<T>
) : MarkovChain<T> {

    private var current: MarkovChainNode<T> = firstNode

    override fun first(): MarkovChainNode<T> = firstNode

    override fun current(): MarkovChainNode<T> = current

    override fun next(): MarkovChainNode<T> {
        current = current.next()
        return current
    }

    override fun reset(): MarkovChainNode<T> {
        current = firstNode
        return current
    }
}
