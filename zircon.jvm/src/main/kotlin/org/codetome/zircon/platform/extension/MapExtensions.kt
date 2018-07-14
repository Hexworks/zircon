@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package org.codetome.zircon.platform.extension

actual fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: V): V {
    return this.getOrDefault(key, defaultValue)
}
