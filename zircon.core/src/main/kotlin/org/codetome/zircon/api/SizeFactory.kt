package org.codetome.zircon.api

expect object SizeFactory {

    fun create(xLength: Int, yLength: Int): Size
}
