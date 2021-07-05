package org.hexworks.zircon.api.util.markovchain

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.random.Random

@Suppress("DataClassPrivateConstructor")
class DefaultMarkovChainNode<T : Any>(
    private val data: T? = null,
    private val random: Random = Random(5234321)
) : MarkovChainNode<T> {

    private val nextNodes: MutableList<Pair<Double, MarkovChainNode<T>>> = mutableListOf()
    private var maybeData: Maybe<T> = Maybe.ofNullable(data)

    override val id: UUID = UUIDFactory.randomUUID()

    override fun next(): MarkovChainNode<T> {
        val rnd: Int = random.nextInt(accuracy)
        var prev = 0
        nextNodes.forEach { (probability, node) ->
            val currentProb = probability.times(accuracy).toInt()
            val currentEnd = prev + currentProb
            if (rnd in prev.rangeTo(currentEnd)) {
                return node
            }
            prev = currentEnd
        }
        return this
    }

    override fun data() = maybeData

    override fun dataOrNull(): T? = data

    override fun addNext(probability: Double, nextNode: MarkovChainNode<T>): MarkovChainNode<T> {
        val currentTotal = nextNodes.map { it.first }.foldRight(0.0, Double::plus)
        require(currentTotal + probability <= 1) {
            "The total of probability values can't be bigger than 1."
        }
        nextNodes.add(probability to nextNode)
        return this
    }

    override fun setData(data: T): MarkovChainNode<T> {
        this.maybeData = Maybe.of(data)
        return this
    }

    companion object {

        private const val accuracy = 10000
    }

}
