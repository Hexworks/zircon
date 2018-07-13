package org.codetome.zircon.internal.multiplatform.extensions

expect fun StringBuilder.deleteCharAt(index: Int): StringBuilder

expect fun StringBuilder.delete(start: Int, end: Int): StringBuilder

expect fun StringBuilder.insert(offset: Int, str: String): StringBuilder
