package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.api.util.Random

expect object RandomFactory {

    fun create(): Random
}
