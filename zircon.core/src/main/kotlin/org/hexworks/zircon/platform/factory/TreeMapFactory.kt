package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.TreeMap

expect object TreeMapFactory {

    fun <K, V> create(): TreeMap<K, V>

}
