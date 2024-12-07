package common


fun replaceCharAt(s: String, index: Int, newChar: Char): String {
    if (index !in s.indices) {
        throw IndexOutOfBoundsException("Index $index is out of bounds for string $s")
    }
    val stringBuilder = StringBuilder(s)
    stringBuilder[index] = newChar
    return stringBuilder.toString()
}

fun String.replaceCharAt(index: Int, newChar: Char) = replaceCharAt(this, index, newChar)