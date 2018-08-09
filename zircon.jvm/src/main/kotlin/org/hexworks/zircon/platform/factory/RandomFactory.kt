package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.api.util.Random
import org.hexworks.zircon.api.util.DefaultRandom

actual object RandomFactory {

    actual fun create(): Random = DefaultRandom()

}
