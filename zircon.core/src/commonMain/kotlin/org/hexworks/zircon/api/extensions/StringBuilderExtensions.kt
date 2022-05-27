@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package org.hexworks.zircon.api.extensions

fun StringBuilder.deleteCharAt(index: Int): StringBuilder {
    return this.deleteCharAt(index)
}

fun StringBuilder.delete(start: Int, end: Int): StringBuilder {
    return this.delete(start, end)
}

fun StringBuilder.insert(offset: Int, str: String): StringBuilder {
    return this.insert(offset, str)
}
