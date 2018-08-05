package org.codetome.zircon.platform.factory

import org.codetome.zircon.jvm.api.util.DefaultRandom
import org.codetome.zircon.api.util.Random

object RandomFactory {

    fun create(): Random = DefaultRandom()

}
