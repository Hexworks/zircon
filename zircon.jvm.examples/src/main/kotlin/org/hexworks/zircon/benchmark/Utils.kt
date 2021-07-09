package org.hexworks.zircon.benchmark

import org.openjdk.jol.info.ClassLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.LongStream

private val random = Random()
private val formatter = (NumberFormat.getInstance() as DecimalFormat).apply {
    decimalFormatSymbols.groupingSeparator = '.'
}

fun generateRandomNumbers(count: Int): LongStream {
    return random.longs(count.toLong())
}

fun printRuntimeOf(note: String = "", fn: () -> Unit) {
    printRuntimeOf(TimeUnit.MILLISECONDS, note, fn)
}

fun printRuntimeOf(timeUnit: TimeUnit, note: String = "", fn: () -> Unit) {
    println(note + measureRuntimeOf(timeUnit, fn))
}

fun printObjectInfoOf(obj: Any) {
    println(ClassLayout.parseInstance(obj).toPrintable())
}

fun calculateSizeOfRec(obj: Any, level: Int = 0): Long {
    val layout = ClassLayout.parseInstance(obj)
    var restSize = 0L
    layout.fields().filter { it.name() != "value" }.forEach { fieldLayout ->
        restSize += fieldLayout.size()
        restSize += obj::class.members
            .firstOrNull {
                it.name == fieldLayout.name()
            }?.call(obj)?.let { calculateSizeOfRec(it, level + 1) } ?: 0
    }
    return layout.instanceSize() + restSize
}

fun calculateSizeOf(obj: Any) = ClassLayout.parseInstance(obj).instanceSize()

fun measureRuntimeOf(timeUnit: TimeUnit, fn: () -> Unit): RunTime {
    val start = System.nanoTime()
    fn()
    val end = System.nanoTime()
    return RunTime(
        timeUnit.convert(end - start, TimeUnit.NANOSECONDS),
        timeUnit
    )
}

fun measureRuntimeOf(fn: () -> Unit): RunTime {
    return measureRuntimeOf(TimeUnit.MILLISECONDS, fn)
}

class RunTime(val time: Long, val timeUnit: TimeUnit) {
    override fun toString(): String {
        val tu: String? = when (timeUnit) {
            TimeUnit.NANOSECONDS -> "ns"
            TimeUnit.MICROSECONDS -> "us"
            TimeUnit.MILLISECONDS -> "ms"
            TimeUnit.SECONDS -> "s"
            TimeUnit.MINUTES -> "m"
            TimeUnit.HOURS -> "h"
            TimeUnit.DAYS -> "d"
        }
        return String.format("%s%s", formatter.format(time), tu)
    }
}
