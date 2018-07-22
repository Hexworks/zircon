@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package org.codetome.zircon.platform.extension

expect fun StringBuilder.deleteCharAt(index: Int): StringBuilder

expect fun StringBuilder.delete(start: Int, end: Int): StringBuilder

expect fun StringBuilder.insert(offset: Int, str: String): StringBuilder
