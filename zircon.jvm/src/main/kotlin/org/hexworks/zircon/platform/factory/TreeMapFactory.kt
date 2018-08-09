package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.internal.util.DefaultTreeMap

actual object TreeMapFactory {

    actual fun <K, V> create(): TreeMap<K, V> {
        return DefaultTreeMap()
    }

}
