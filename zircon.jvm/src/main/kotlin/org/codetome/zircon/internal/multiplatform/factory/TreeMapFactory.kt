package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.api.TreeMap
import org.codetome.zircon.internal.multiplatform.impl.JvmTreeMap

actual object TreeMapFactory {

    actual fun <K, V> create(): TreeMap<K, V> {
        return JvmTreeMap()
    }

}
