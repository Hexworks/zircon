package org.hexworks.zircon.api.util

import org.hexworks.zircon.platform.factory.RandomFactory

/**
 * Utility for generating pseudo random numbers
 */
interface Random {

    fun nextInt(bound: Int): Int

    fun nextInt(): Int

    companion object {

        fun create(): Random = RandomFactory.create()
    }
}
