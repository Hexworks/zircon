package org.hexworks.zircon.api.util

class DefaultRandom : Random {

    private val backend = java.util.Random()

    override fun nextInt(bound: Int) = backend.nextInt(bound)

    override fun nextInt() = backend.nextInt()

}
