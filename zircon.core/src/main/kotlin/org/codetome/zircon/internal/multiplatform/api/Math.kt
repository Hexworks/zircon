package org.codetome.zircon.internal.multiplatform.api

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

    fun abs(a: Int): Int {
        return if (a < 0) -a else a
    }

    fun abs(a: Long): Long {
        return if (a < 0) -a else a
    }
}
