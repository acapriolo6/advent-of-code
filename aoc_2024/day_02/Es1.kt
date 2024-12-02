package aoc_2024.day_02

import common.readLines
import kotlin.math.abs


fun main() {
    val rawInput = readLines("${Es01::class.java.packageName.replace(".", "/")}/input_01.txt", true)
    val lines = createInputLists(rawInput)
    val result = Es01().calculateResult(lines)
    println(result)
}
class Es01 {
    fun calculateResult(lines: List<List<Int>>): Int {
        var count = 0
        for (line in lines) {
            count += if (isSafe(line)) 1 else 0
        }
        return count
    }
}

fun createInputLists(rawInput: List<String>) : List<List<Int>> {
    return rawInput.map {
        it.split(" ").map { el -> el.toInt() }
    }
}

fun isSafe(array: List<Int>): Boolean {

    val isAscending = array[0] < array[1]

    for (index in 1..array.lastIndex) {
        val distance = array[index - 1] - array[index]
        val absDistance = abs(distance)

        if (absDistance !in 1..3 || (isAscending && distance > 0) || (!isAscending && distance < 0)) {
            return false
        }
    }
    return true
}
