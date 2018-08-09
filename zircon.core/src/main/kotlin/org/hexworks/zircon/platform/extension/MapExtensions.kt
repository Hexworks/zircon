@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package org.hexworks.zircon.platform.extension

expect fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: V): V
