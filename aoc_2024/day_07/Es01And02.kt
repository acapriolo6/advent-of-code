package aoc_2024.day_07

import common.readLines


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_02.txt", false)
    val result = Es01And02().calculateResult(rawInput)
    val resultPart2 = Es01And02().calculateResult(rawInput, true)
    println(result)
    println(resultPart2)
}

class Es01And02 {

    private fun calculateResult(numbers: List<Long>, expectedResult: Long, prevResult: Long? = null, enableConcatenationOperator: Boolean = false) : Long? {
        if (numbers.isEmpty()) {
            return prevResult
        }
        val n1 = numbers.first()
        val subList = numbers.subList(1, numbers.size)

        val res1 = calculateResult(subList, expectedResult, (prevResult ?: 0) + n1, enableConcatenationOperator)
        if (res1 == expectedResult) {
            return expectedResult
        }
        if (prevResult != null) {
            val res2 = calculateResult(subList, expectedResult, prevResult * n1, enableConcatenationOperator)
            if (res2 == expectedResult) {
                return expectedResult
            }
            if (enableConcatenationOperator) {
                val res3 = calculateResult(subList, expectedResult, "$prevResult$n1".toLong(), true)
                if (res3 == expectedResult) {
                    return expectedResult
                }
            }
        }
        return null
    }

    fun calculateResult(lines: List<String>, enableConcatenationOperator: Boolean = false): Long {
        return parseInput(lines)
            .filter { it.first == calculateResult(it.second, it.first, null, enableConcatenationOperator) }
            .map { it.first }
            .reduce { acc, pair -> acc + pair }
    }

    private fun parseInput(lines: List<String>): List<Pair<Long, List<Long>>> {
        val result = mutableListOf<Pair<Long, List<Long>>>()
        for (line in lines) {
            val split = line.split(": ")
            val expectedRes = split.first().toLong()
            val numbers = split[1].split(" ").map { it.toLong() }
            result.add(expectedRes to numbers)
        }
        return result
    }
}
