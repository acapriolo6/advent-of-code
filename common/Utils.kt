package common


fun String.replaceCharAt(index: Int, newChar: Char) : String {
    if (index !in this.indices) {
        throw IndexOutOfBoundsException("Index $index is out of bounds for string $this")
    }
    val stringBuilder = StringBuilder(this)
    stringBuilder[index] = newChar
    return stringBuilder.toString()
}