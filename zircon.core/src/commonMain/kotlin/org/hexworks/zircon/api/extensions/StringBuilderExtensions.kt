package org.hexworks.zircon.api.extensions

fun StringBuilder.deleteCharAt(index: Int): StringBuilder {
    return this.deleteCharAt(index)
}

fun StringBuilder.delete(start: Int, end: Int): StringBuilder {
    return this.delete(start, end)
}
