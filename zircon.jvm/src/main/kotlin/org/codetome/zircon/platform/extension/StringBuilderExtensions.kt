@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package org.codetome.zircon.platform.extension

actual fun StringBuilder.deleteCharAt(index: Int): StringBuilder {
    return this.deleteCharAt(index)
}

actual fun StringBuilder.delete(start: Int, end: Int): StringBuilder {
    return this.delete(start, end)
}

actual fun StringBuilder.insert(offset: Int, str: String): StringBuilder {
    return this.insert(offset, str)
}
