package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.Random
import org.codetome.zircon.api.util.DefaultRandom

actual object RandomFactory {

    actual fun create(): Random = DefaultRandom()

}
