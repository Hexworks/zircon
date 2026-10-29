import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.examples.Demo
import org.hexworks.zircon.examples.demos
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.terminal.runTerminal
import org.hexworks.zircon.terminal.systemTty
import kotlin.time.TimeSource

/**
 * Runs the demos in the terminal. `main [demo name]` starts on that demo (default: the
 * first); the tab bar switches between the ones a terminal can show. Ctrl+C quits and
 * prints the frame rate of the last demo shown.
 */
fun main(args: Array<String>) {
    val available = demos.filter { it.textOnly }
    var demo = args.firstOrNull()?.let { name ->
        available.firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: error("No demo named '$name'. Available: ${available.joinToString { it.name }}")
    } ?: available.first()
    Resources.loader = FileResourceLoader(filesDirectory())

    var next: Demo? = demo
    while (next != null) {
        demo = next
        next = null
        val size = systemTty().size()
        val shown = runBlocking { examplesScreen(demo, size, available) { selected -> next = selected } }
        if (next != null) continue
        val started = TimeSource.Monotonic.markNow()
        val session = runTerminal(shown.grid, beforeFrame = {
            shown.onFrame()
            if (next != null) shown.grid.close()
        })
        val seconds = started.elapsedNow().inWholeMilliseconds / 1000.0
        val frames = session.framesRendered
        if (next == null && seconds > 0 && frames > 0) {
            printError(
                "${demo.name}: $frames frames in ${seconds}s (${(frames / seconds).toInt()} fps), grid ${size.width}x${size.height}, " +
                    "render ${(session.renderMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                    "encode ${(session.encodeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                    "write ${(session.writeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                    "${session.bytesWritten / frames / 1024} KB/frame"
            )
        }
    }
}

/** Reads `PROJECT` resources from the demo files directory and `FILESYSTEM` ones as given. */
private class FileResourceLoader(private val root: String) : ResourceLoader {
    override suspend fun loadBytes(resource: Resource): ByteArray = when (resource.resourceType) {
        ResourceType.PROJECT -> readFile(root + "/" + resource.path.trimStart('/'))
        ResourceType.FILESYSTEM -> readFile(resource.path)
    }
}

private fun filesDirectory(): String = environmentVariable("ZIRCON_EXAMPLES_FILES")
    ?: error("Set ZIRCON_EXAMPLES_FILES to the demo data directory (zircon.examples/resources/files)")

expect fun readFile(path: String): ByteArray
expect fun environmentVariable(name: String): String?
expect fun printError(text: String)
