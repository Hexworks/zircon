package org.codetome.zircon.internal.multiplatform.extensions

expect fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: V): V
