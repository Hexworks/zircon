package org.hexworks.zircon.internal.component

fun String.withNewLinesStripped() = split("\r\n")
    .first()
    .split("\n")
    .first()
