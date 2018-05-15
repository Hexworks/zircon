package org.codetome.zircon.util

object Math {

    fun max(a: Int, b: Int): Int {
        return if (a >= b) a else b
    }

    fun max(a: Long, b: Long): Long {
        return if (a >= b) a else b
    }

    fun min(a: Int, b: Int): Int {
        return if (a <= b) a else b
    }

    fun min(a: Long, b: Long): Long {
        return if (a <= b) a else b
    }
}
