package org.codetome.zircon.api.util

import org.codetome.zircon.platform.factory.RandomFactory

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
