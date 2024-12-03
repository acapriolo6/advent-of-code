package common
import java.io.File

fun readLines(filePath: String, debugEnabled: Boolean = false): List<String> {
    val lines = File(filePath).readLines()
    if (debugEnabled) {
        for (line in lines) {
            println("DEBUG: $line")
        }
    }
    return lines
}