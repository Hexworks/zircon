import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.examples.MIN_SIZE
import org.hexworks.zircon.examples.EXAMPLE_GROUPS
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.terminal.runTerminal
import org.hexworks.zircon.terminal.systemTty
import kotlin.time.TimeSource

/**
 * Runs the examples in the terminal. `main [example name]` starts on that example; the tab
 * bars switch between packages and examples. Ctrl+C quits and prints the frame rate.
 */
fun main(args: Array<String>) {
    Resources.loader = FileResourceLoader(filesDirectory())
    val textOnly = EXAMPLE_GROUPS.flatMap { it.examples }.filter { it.textOnly }
    val startAt = args.firstOrNull()?.let { name ->
        textOnly.firstOrNull { it.name.equals(name, ignoreCase = true) }?.name
            ?: error("No example named '$name'. Available: ${textOnly.joinToString { it.name }}")
    }
    val size = systemTty().size()
    if (size.width < MIN_SIZE.width || size.height < MIN_SIZE.height) {
        printError("The examples need a terminal of at least ${MIN_SIZE.width}x${MIN_SIZE.height} cells; this one is ${size.width}x${size.height}.")
        return
    }
    val shown = runBlocking { examplesScreen(size, isTerminal = true, startAt = startAt) }
    val started = TimeSource.Monotonic.markNow()
    // Errors Zircon contained are logged; runTerminal prints the log once the terminal is restored.
    // The terminal's own colors are the system palette; the app starts on the selector's
    // `System` entry, so they show from the first frame and follow the terminal's theme switches.
    val session = runTerminal(shown.grid, beforeFrame = shown.onFrame)
    val seconds = started.elapsedNow().inWholeMilliseconds / 1000.0
    val frames = session.framesRendered
    if (seconds > 0 && frames > 0) {
        printError(
            "${startAt ?: "Examples"}: $frames frames in ${seconds}s (${(frames / seconds).toInt()} fps), grid ${size.width}x${size.height}, " +
                "render ${(session.renderMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "encode ${(session.encodeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "write ${(session.writeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "${session.bytesWritten / frames} bytes/frame" +
                if (session.hasKittyKeyboard) "" else ", legacy keys"
        )
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
