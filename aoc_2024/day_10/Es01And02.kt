package aoc_2024.day_10

import common.readLines


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_02.txt", false)
    val result = Es01And02().calculateResult(rawInput)
    println("part1=${result.first} part2=${result.second}")
}

class Es01And02 {

    private fun readInput(input: List<String>) : List<List<Int>> {
        val result = mutableListOf<MutableList<Int>>()
        for (line in input) {
            val singleLine = mutableListOf<Int>()
            for (char in line) {
                singleLine.add(char.digitToInt())
            }
            result.add(singleLine)
        }
        return result
    }

    fun calculateResult(input: List<String>) : Pair<Long, Long> {
        val map = readInput(input)
        val result = resolvePart1And2(map)
        return result
    }

    private fun resolvePart1And2(map: List<List<Int>>): Pair<Long, Long> {
        var resultPart1 = 0L
        var resultPart2 = 0L
        val positionCount = mutableMapOf<Pair<Int, Int>, Set<Pair<Int, Int>>>()
        val positionCountPart2 = mutableMapOf<Pair<Int, Int>, Long>()
        for ((i, line) in map.withIndex()) {
            for ((j, value) in line.withIndex()) {
                if (value == 0) {
                    resultPart1 += resolvePart1(map, (i) to (j), value, positionCount).size
                    resultPart2 += resolvePart2(map, (i) to (j), value, positionCountPart2)
                }
            }
        }
        return resultPart1 to resultPart2
    }

    private fun resolvePart2(map: List<List<Int>>, pos: Pair<Int, Int>, expectedValue: Int, positionCount: MutableMap<Pair<Int, Int>, Long>): Long {
        if (!isInTheRange(map, pos)) {
            println("Pos not in range $pos")
            return 0L
        }
        val currentValue = map[pos.first][pos.second]
        if (currentValue != expectedValue) {
            println("currentValue != expectedValue $currentValue $expectedValue for pos $pos")
            return 0L
        }
        if (expectedValue  == 9) {
            return 1
        }
        if (positionCount.containsKey(pos)) {
            return positionCount[pos]!!
        }
        var result = 0L
        val i = pos.first
        val j = pos.second
        val posToChecks = listOf((i + 1) to (j), (i - 1) to (j), (i) to (j + 1), (i) to (j - 1))
        for (posToCheck in posToChecks) {
            val temp = resolvePart2(map, posToCheck, currentValue + 1, positionCount)
            result += (temp)
        }
        positionCount[pos] = result
        return result
    }

    private fun resolvePart1(map: List<List<Int>>, pos: Pair<Int, Int>, expectedValue: Int, positionCount: MutableMap<Pair<Int, Int>, Set<Pair<Int, Int>>>): Set<Pair<Int, Int>> {
        if (!isInTheRange(map, pos)) {
            println("Pos not in range $pos")
            return emptySet()
        }
        val currentValue = map[pos.first][pos.second]
        if (currentValue != expectedValue) {
            println("currentValue != expectedValue $currentValue $expectedValue for pos $pos")
            return emptySet()
        }
        if (expectedValue  == 9) {
            return setOf(pos)
        }
        if (positionCount.containsKey(pos)) {
            return positionCount[pos]!!
        }
        val result = mutableSetOf<Pair<Int, Int>>()
        val i = pos.first
        val j = pos.second
        val posToChecks = listOf((i + 1) to (j), (i - 1) to (j), (i) to (j + 1), (i) to (j - 1))
        for (posToCheck in posToChecks) {
            val temp = resolvePart1(map, posToCheck, currentValue + 1, positionCount)
            result.addAll(temp)
        }
        positionCount[pos] = result
        return result
    }


    private fun isInTheRange(map: List<List<Int>>, pos: Pair<Int, Int>): Boolean {
        if (pos.first >= map.size || pos.first < 0) return false
        val temp = map[pos.first]
        if (pos.second >= temp.size || pos.second < 0) return false
        return true
    }


}
