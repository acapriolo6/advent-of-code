package aoc_2024.day_03

import common.readLines


fun main() {
    val rawInput = readLines("${Es01::class.java.packageName.replace(".", "/")}/input_01.txt", false)
    val result = Es01().calculateResult(rawInput)
    println(result)
}

class Es01 {
    fun calculateResult(lines: List<String>): Long {
        return lines
            .map { calculate(it) }
            .reduce { x, y -> x + y }
    }

    private fun calculate(input: String): Long {
        return """mul\([0-9]+,[0-9]+\)""".toRegex()
            .findAll(input)
            .map { it.value }
            .map { extractNumbers(it).reduce { x, y -> x * y } }
            .reduce { x, y -> x + y }
    }
}

fun extractNumbers(input: String): List<Long> {
    val regex = "\\d+".toRegex()
    return regex.findAll(input)
        .map { it.value.toLong() }
        .toList()
}
