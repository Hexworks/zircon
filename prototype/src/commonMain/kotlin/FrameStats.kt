import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.time.TimeSource

/** Rolling averages over the last [window] frames: wall-clock fps and time spent drawing. */
class FrameStats(private val window: Int = 60) {
    var fps by mutableStateOf(0.0)
        private set
    var drawMs by mutableStateOf(0.0)
        private set
    var fillMs by mutableStateOf(0.0)
        private set
    var uploadMs by mutableStateOf(0.0)
        private set

    private val frameNs = LongArray(window)
    private val drawNs = LongArray(window)
    private val fillNs = LongArray(window)
    private val uploadNs = LongArray(window)
    private var idx = 0
    private var filled = 0
    private var lastFrame = 0L

    fun reset() {
        frameNs.fill(0); drawNs.fill(0); fillNs.fill(0); uploadNs.fill(0)
        idx = 0; filled = 0; lastFrame = 0L
        fps = 0.0; drawMs = 0.0; fillMs = 0.0; uploadMs = 0.0
    }

    fun frame(nowNanos: Long) {
        if (lastFrame != 0L) {
            frameNs[idx] = nowNanos - lastFrame
            idx = (idx + 1) % window
            if (filled < window) filled++
            publish()
        }
        lastFrame = nowNanos
    }

    inline fun <T> measureDraw(block: () -> T): T {
        val mark = TimeSource.Monotonic.markNow()
        val r = block()
        recordDraw(mark.elapsedNow().inWholeNanoseconds)
        return r
    }

    inline fun <T> measureFill(block: () -> T): T {
        val mark = TimeSource.Monotonic.markNow()
        val r = block()
        recordFill(mark.elapsedNow().inWholeNanoseconds)
        return r
    }

    inline fun <T> measureUpload(block: () -> T): T {
        val mark = TimeSource.Monotonic.markNow()
        val r = block()
        recordUpload(mark.elapsedNow().inWholeNanoseconds)
        return r
    }

    fun recordUpload(ns: Long) {
        uploadNs[idx] = ns
    }

    fun recordDraw(ns: Long) {
        drawNs[idx] = ns
    }

    fun recordFill(ns: Long) {
        fillNs[idx] = ns
    }

    private fun publish() {
        var f = 0L
        var d = 0L
        var g = 0L
        var u = 0L
        for (i in 0 until filled) {
            f += frameNs[i]; d += drawNs[i]; g += fillNs[i]; u += uploadNs[i]
        }
        val n = filled.coerceAtLeast(1)
        fps = if (f > 0) 1e9 * n / f else 0.0
        drawMs = d / n / 1e6
        fillMs = g / n / 1e6
        uploadMs = u / n / 1e6
    }
}
