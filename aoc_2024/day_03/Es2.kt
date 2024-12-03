package aoc_2024.day_03

import common.readLines

fun main() {
    val rawInput = readLines("${Es02::class.java.packageName.replace(".", "/")}/input_01.txt", false)
    val result = Es02().calculateResult(rawInput)
    println(result)
}

class Es02 {
    fun calculateResult(lines: List<String>): Long {
        var enabled = true
        var count: Long = 0
        for (line in lines) {
            val result = calculate(line, enabled)
            count += result.first
            enabled = result.second
        }
        return count
    }

    private fun calculate(input: String, startingEnabled: Boolean = true): Pair<Long, Boolean> {
        val values = """mul\([0-9]+,[0-9]+\)|do\(\)|don't\(\)""".toRegex()
            .findAll(input).map { it.value }

        var enabled = startingEnabled
        var count: Long = 0
        var doCount: Long = 0
        var dontCount: Long = 0
        for (value in values) {
            if (value == "don't()") {
                dontCount++
                enabled = false
            } else if (value == "do()") {
                doCount++
                enabled = true
            } else {
                if (enabled) {
                    count += extractNumbers(value)
                        .reduce { x, y -> x * y }
                }
            }
        }

        return Pair(count, enabled)
    }
}
