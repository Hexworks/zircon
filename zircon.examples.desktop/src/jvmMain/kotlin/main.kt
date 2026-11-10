import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.reload.AfterHotReloadEffect
import org.jetbrains.compose.reload.DelicateHotReloadApi
import org.hexworks.zircon.examples.compose.ExamplesApp

@OptIn(DelicateHotReloadApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Zircon examples",
        state = rememberWindowState(placement = WindowPlacement.Maximized),
    ) {
        // The Zircon screen is built once and kept, so reloaded code only shows on a
        // rebuild; bumping this after each hot reload triggers one. A no-op without hot reload.
        var reloads by remember { mutableIntStateOf(0) }
        AfterHotReloadEffect { reloads++ }
        ExamplesApp(rebuildKey = reloads)
    }
}
