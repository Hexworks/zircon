import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.hexworks.zircon.examples.compose.ExamplesApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Zircon examples",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
    ) {
        ExamplesApp()
    }
}
