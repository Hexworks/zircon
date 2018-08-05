package org.codetome.zircon.jvm.api.util

import org.codetome.zircon.api.util.Random

class DefaultRandom : Random {

    private val backend = java.util.Random()

    override fun nextInt(bound: Int) = backend.nextInt(bound)

    override fun nextInt() = backend.nextInt()

}
