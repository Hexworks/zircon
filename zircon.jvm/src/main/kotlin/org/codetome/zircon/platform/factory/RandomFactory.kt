package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.DefaultRandom
import org.codetome.zircon.api.util.Random

actual object RandomFactory {

    actual fun create(): Random = DefaultRandom()

}
