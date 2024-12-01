package aoc_2024.day_01

import common.readLines
import kotlin.math.abs


fun main() {
    val rawInput = readLines("${Es01::class.java.packageName.replace(".", "/")}/input_01.txt", true)
    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()
    createInputLists(rawInput, leftList, rightList)
    val result = Es01().calculateResult(leftList, rightList)
    println(result)
}
class Es01 {
    fun calculateResult(leftList: List<Int>, rightList: List<Int>): Int {
        val sortedLeftList = leftList.sorted()
        val sortedRightList = rightList.sorted()
        var result = 0
        for (i in sortedRightList.indices) {
            result += abs(sortedLeftList[i] - sortedRightList[i])
        }
        return result
    }
}

fun createInputLists(rawInput: List<String>, leftList: MutableList<Int>, rightList: MutableList<Int>) {
    rawInput.forEach {
        val split = it.split(" ")
        leftList.add(split.first().toInt())
        rightList.add(split.last().toInt())
    }
}
