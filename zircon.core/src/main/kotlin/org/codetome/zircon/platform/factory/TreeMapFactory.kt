package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.TreeMap

expect object TreeMapFactory {

    fun <K, V> create(): TreeMap<K, V>
}
