package org.hexworks.zircon.internal.util

import kotlin.time.TimeSource

private val start = TimeSource.Monotonic.markNow()

/**
 * Milliseconds elapsed since the process started, from a monotonic clock. Zircon only
 * ever compares timestamps with each other (blink, fades, animation frames), so a
 * monotonic origin is both sufficient and safer than wall-clock time.
 */
internal fun nowMillis(): Long = start.elapsedNow().inWholeMilliseconds
