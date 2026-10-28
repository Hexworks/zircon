import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val search = window.location.search
        val only = Regex("only=([A-Z_]+)").find(search)?.groupValues?.get(1)?.let { Path.valueOf(it) }
        App(auto = search.contains("auto"), only = only)
    }
}
