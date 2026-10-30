import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.examples.ExamplesSelection
import org.hexworks.zircon.examples.MIN_SIZE
import org.hexworks.zircon.examples.examplePackages
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
    var selection = ExamplesSelection.DEFAULT
    args.firstOrNull()?.let { name ->
        val pkg = examplePackages.firstOrNull { pkg -> pkg.examples.any { it.textOnly && it.name.equals(name, ignoreCase = true) } }
            ?: error("No example named '$name'. Available: ${examplePackages.flatMap { it.examples }.filter { it.textOnly }.joinToString { it.name }}")
        selection = selection.copy(packageName = pkg.name, exampleName = pkg.examples.first { it.name.equals(name, ignoreCase = true) }.name)
    }
    val size = systemTty().size()
    if (size.width < MIN_SIZE.width || size.height < MIN_SIZE.height) {
        printError("The examples need a terminal of at least ${MIN_SIZE.width}x${MIN_SIZE.height} cells; this one is ${size.width}x${size.height}.")
        return
    }
    val shown = runBlocking { examplesScreen(selection, size, textOnly = true) }
    val started = TimeSource.Monotonic.markNow()
    val session = runTerminal(shown.grid, beforeFrame = shown.onFrame)
    val seconds = started.elapsedNow().inWholeMilliseconds / 1000.0
    val frames = session.framesRendered
    if (seconds > 0 && frames > 0) {
        printError(
            "${shown.selection.exampleName}: $frames frames in ${seconds}s (${(frames / seconds).toInt()} fps), grid ${size.width}x${size.height}, " +
                "render ${(session.renderMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "encode ${(session.encodeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "write ${(session.writeMillis / frames * 100).toInt() / 100.0} ms/frame, " +
                "${session.bytesWritten / frames} bytes/frame"
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
