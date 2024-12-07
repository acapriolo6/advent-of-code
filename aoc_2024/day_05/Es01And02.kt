package aoc_2024.day_05

import common.readLines


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_01.txt", false)
    val result = Es01And02().countCorrects(rawInput)
    val result2 = Es01And02().countCorrectsV2(rawInput)
    println("part1=${result} part2=${result2}")
}

class Es01And02 {
    fun countCorrects(input: List<String>) : Int {
        val orders = mutableMapOf<String, Int>()
        var i = 0
        var count = 0
        for (line in input) {
            if (line.contains("|")) {
                orders[line] = i
                i++
            } else if (line.isNotBlank()){
                val res = checkIfCorrectAndReturnMiddle(line, orders)
                count += res
            }
        }
        return count
    }

    fun countCorrectsV2(input: List<String>) : Int {
        val orders = mutableMapOf<String, Int>()
        var i = 0
        var count = 0
        for (line in input) {
            if (line.contains("|")) {
                orders[line] = i
                i++
            } else if (line.isNotBlank()){
                val res = reorderIfWrong(line.split(",").map { it.toInt() }, orders)
                count += if (res.first) res.second[res.second.size / 2] else 0
            }
        }
        return count
    }

    private fun checkIfCorrectAndReturnMiddle(line: String, orders: Map<String, Int>) : Int {
        val elements = line.split(",").map { it.toInt() }
        if (elements.isEmpty()) {
            return 0
        }
        for (i in 1 until elements.size) {
            val prevNumber = elements[i-1]
            val number = elements[i]
            val wrongOrder = orders["$number|$prevNumber"]
            if (wrongOrder != null) {
                return 0
            }
        }
        return elements[elements.size / 2]
    }

    private fun reorderIfWrong(input: List<Int>, rules: Map<String, Int>) : Pair<Boolean, List<Int>> {
        var elements = input.toMutableList()
        if (elements.isEmpty()) {
            return Pair(false, emptyList())
        }
        var reordered = false
        for (i in 1 until elements.size) {
            val prevNumber = elements[i-1]
            val number = elements[i]
            val wrongOrder = rules["$number|$prevNumber"]
            if (wrongOrder != null) {
                val prevList = elements.subList(0, i-1)
                reordered = true
                elements = (reorderIfWrong(prevList + listOf(number, prevNumber), rules).second + elements.subList(i+1, elements.size)).toMutableList()
            }
        }
        return Pair(reordered, elements)
    }

}

