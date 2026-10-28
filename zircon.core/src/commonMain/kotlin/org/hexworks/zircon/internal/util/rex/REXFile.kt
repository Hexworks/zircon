package org.hexworks.zircon.internal.util.rex

/**
 * Represents a REX Paint File, which contains version and [REXLayer] information.
 */
public data class REXFile(
    val version: Int,
    val numberOfLayers: Int,
    val layers: List<REXLayer>
)
