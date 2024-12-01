package aoc_2024.day_02

import common.readLines

fun main() {
    val rawInput = readLines("${Es02::class.java.packageName.replace(".", "/")}/input_02.txt", true)
    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()
    createInputLists(rawInput, leftList, rightList)
    val result = Es02().calculateResult(leftList, rightList)
    println(result)
}

class Es02 {
    fun calculateResult(leftList: List<Int>, rightList: List<Int>): Long {
        val mapOfElement = mutableMapOf<Int, Int>()
        rightList.forEach { mapOfElement[it] = mapOfElement.getOrDefault(it, 0) + 1  }
        var result: Long = 0
        for (i in leftList.indices) {
            result += (leftList[i] * mapOfElement.getOrDefault(leftList[i], 0))
        }
        return result
    }
}
