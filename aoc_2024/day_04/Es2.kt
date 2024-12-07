package aoc_2024.day_04

import common.readLines

fun main() {
    val rawInput = readLines("${Es02::class.java.packageName.replace(".", "/")}/input_01.txt", false)
    val result = Es02().countWords(rawInput, "MAS")
    println(result)
}

class Es02 {
    fun countWords(input: List<String>, word: String): Int {
        if (word.isEmpty() || input.isEmpty()) {
            return 0
        }
        var count = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == word[word.length / 2 ]) {
                    val firstWordPart = word.substring(0, word.length / 2)
                    val secondWordPart = word.substring(word.length / 2 +1, word.length)
                    val principalDiagonal = countWords(input, firstWordPart, i, j, i-1, j-1) + countWords(input, secondWordPart, i, j, i+1, j+1)
                    val principalDiagonalReversed = countWords(input, secondWordPart, i, j, i-1, j-1) + countWords(input, firstWordPart, i, j, i+1, j+1)
                    val secondaryDiagonal = countWords(input, firstWordPart, i, j, i-1, j+1) + countWords(input, secondWordPart, i, j, i+1, j-1)
                    val secondaryDiagonalReversed = countWords(input, secondWordPart, i, j, i-1, j+1) + countWords(input, firstWordPart, i, j, i+1, j-1)

                    if ((principalDiagonal == 2 || principalDiagonalReversed == 2) && (secondaryDiagonal == 2 || secondaryDiagonalReversed == 2)) {
                        count++
                    }
                }
            }
        }
        return count
    }
}
