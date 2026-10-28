import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.system.exitProcess

fun main() = application {
    val auto = System.getenv("PROTO_AUTO") == "1"
    val only = System.getenv("PROTO_ONLY")?.let { Path.valueOf(it) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "zircon renderer prototype",
        state = rememberWindowState(size = DpSize((COLS * 8).dp, (ROWS * 8 + 40).dp)),
    ) {
        App(auto = auto, only = only, onDone = { exitProcess(0) })
    }
}
