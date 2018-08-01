package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.TreeMap
import org.codetome.zircon.internal.util.DefaultTreeMap
import java.util.*

object TreeMapFactory {

    fun <K, V> create(): TreeMap<K, V> {
        return DefaultTreeMap()
    }

}
