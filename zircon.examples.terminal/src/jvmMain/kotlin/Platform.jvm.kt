import java.io.File

actual fun readFile(path: String): ByteArray = File(path).readBytes()
actual fun environmentVariable(name: String): String? = System.getenv(name)
actual fun printError(text: String) = System.err.println(text)
