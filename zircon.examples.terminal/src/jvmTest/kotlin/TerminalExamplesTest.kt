import io.kotest.matchers.shouldBe
import org.hexworks.zircon.examples.examplePackages
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.Resources
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.ExamplesSelection
import org.hexworks.zircon.examples.MIN_SIZE
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.terminal.CellBuffer
import org.hexworks.zircon.terminal.TerminalRenderer
import java.io.File
import kotlin.test.Test
import kotlin.time.TimeSource

/**
 * Renders the examples through the terminal renderer (no tty) so a broken example or an
 * example that draws nothing shows up in CI.
 */
class TerminalExamplesTest {

    init {
        Resources.loader = ResourceLoader { resource: Resource ->
            File("../zircon.examples/resources/files/" + resource.path.trimStart('/')).readBytes()
        }
    }

    /** The content area as text; colour-only cells (space on a non-theme background) show as `#`. */
    /** Examples that show nothing until a delay passes or the user types. */
    private val EMPTY_AT_FIRST = setOf("modifiers / Delay", "other / Typing")

    private fun CellBuffer.contentText(): String {
        val themeBackground = backgrounds[index(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)]
        return (EXAMPLE_TOP until height).joinToString("\n") { y ->
            (EXAMPLE_TAB_WIDTH until width).joinToString("") { x ->
                val i = index(x, y)
                if (codes[i] == ' '.code && backgrounds[i] != themeBackground) "#" else codes[i].toChar().toString()
            }.trimEnd()
        }
    }

    /** Every text-only example, rendered for a few ticks, must put something into its content area. */
    @Test
    fun everyExampleDrawsIntoTheContentArea() {
        val shown = runBlocking { examplesScreen(ExamplesSelection.DEFAULT, MIN_SIZE, textOnly = true) }
        val renderer = TerminalRenderer(shown.grid)
        val frame = CellBuffer(MIN_SIZE.width, MIN_SIZE.height)
        val empty = mutableListOf<String>()
        examplePackages.forEach { pkg ->
            val examples = pkg.examples.filter { it.textOnly }
            if (examples.isEmpty()) return@forEach
            shown.packageBar.selectedKey = pkg.name
            examples.forEach { example ->
                shown.exampleBars.getValue(pkg.name).selectedKey = example.name
                var drawn = 0
                var slowest = 0.0
                repeat(6) {
                    val started = TimeSource.Monotonic.markNow()
                    shown.onFrame()
                    renderer.render(frame)
                    slowest = maxOf(slowest, started.elapsedNow().inWholeMicroseconds / 1000.0)
                    drawn = maxOf(drawn, frame.contentText().count { it != ' ' && it != '\n' })
                    Thread.sleep(40)
                }
                println("${pkg.name} / ${example.name}: $drawn cells, slowest tick ${slowest}ms")
                if (drawn == 0 && "${pkg.name} / ${example.name}" !in EMPTY_AT_FIRST) empty += "${pkg.name} / ${example.name}"
            }
        }
        renderer.close()
        shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
        empty shouldBe emptyList()
    }
}
