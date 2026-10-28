package org.hexworks.zircon.internal.component

public fun String.withNewLinesStripped(): String = split("\r\n")
    .first()
    .split("\n")
    .first()
