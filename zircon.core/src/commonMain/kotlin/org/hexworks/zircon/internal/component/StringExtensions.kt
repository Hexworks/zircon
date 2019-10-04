package org.hexworks.zircon.internal.component

import org.hexworks.zircon.platform.util.SystemUtils

fun String.withNewLinesStripped() = split(SystemUtils.getLineSeparator())
        .first()
        .split("\n")
        .first()
