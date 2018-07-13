package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.api.TreeMap

expect object TreeMapFactory {

    fun <K, V> create(): TreeMap<K, V>
}
