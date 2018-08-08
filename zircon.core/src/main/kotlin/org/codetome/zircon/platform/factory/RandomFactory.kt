package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.Random

expect object RandomFactory {

    fun create(): Random

}
