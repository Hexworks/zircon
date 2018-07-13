package org.codetome.zircon.internal.multiplatform.api

interface TreeMap<K, V> : MutableMap<K, V> {


    fun getOrDefault(key: K, defaultValue: V): V
}
