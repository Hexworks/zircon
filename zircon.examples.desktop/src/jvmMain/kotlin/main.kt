import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.hexworks.zircon.examples.ExamplesApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Zircon examples",
        state = rememberWindowState(size = DpSize(1000.dp, 560.dp)),
    ) {
        ExamplesApp()
    }
}
