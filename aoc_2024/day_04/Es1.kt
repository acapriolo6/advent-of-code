package aoc_2024.day_04

import common.readLines


fun main() {
    val rawInput = readLines("${Es01::class.java.packageName.replace(".", "/")}/input_01.txt", false)
    val result = Es01().countWords(rawInput, "XMAS")
    println(result)
}

class Es01 {
    fun countWords(input: List<String>, word: String): Int {
        if (word.isEmpty() || input.isEmpty()) {
            return 0
        }
        var count = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == word.first()) {
                    count += countWords(input, word.substring(1), i, j, i, j + 1)
                    count += countWords(input, word.substring(1), i, j, i, j - 1)
                    count += countWords(input, word.substring(1), i, j, i-1, j)
                    count += countWords(input, word.substring(1), i, j, i+1, j)
                    count += countWords(input, word.substring(1), i, j, i-1, j-1)
                    count += countWords(input, word.substring(1), i, j, i-1, j+1)
                    count += countWords(input, word.substring(1), i, j, i+1, j-1)
                    count += countWords(input, word.substring(1), i, j, i+1, j+1)
                }
            }
        }
        return count
    }
}

fun countWords(input: List<String>, word: String, prevI: Int, prevJ: Int, i: Int, j: Int): Int {
    if (word.isEmpty()) {
        return 0
    }
    if (i !in input.indices || j !in input[i].indices) {
        return 0
    }
    if (word.length == 1 && input[i][j] == word.last()) {
        return 1
    }
    if (input[i][j] == word.first()) {
        if (prevI == i && prevJ < j)
            return countWords(input, word.substring(1), i, j, i, j+1)
        if (prevI == i && prevJ > j)
            return countWords(input, word.substring(1), i, j, i, j-1)
        if (prevI > i && prevJ == j)
            return countWords(input, word.substring(1), i, j, i-1, j)
        if (prevI < i && prevJ == j)
            return countWords(input, word.substring(1), i, j, i+1, j)
        if (prevI > i && prevJ > j)
            return countWords(input, word.substring(1), i, j, i-1, j-1)
        if (prevI > i && prevJ < j)
            return countWords(input, word.substring(1), i, j, i-1, j+1)
        if (prevI < i && prevJ > j)
            return countWords(input, word.substring(1), i, j, i+1, j-1)
        if (prevI < i && prevJ < j)
            return countWords(input, word.substring(1), i, j, i+1, j+1)
    }
    return 0
}
