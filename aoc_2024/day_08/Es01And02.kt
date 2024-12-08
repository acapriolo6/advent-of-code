package aoc_2024.day_08

import common.readLines
import kotlin.math.abs


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_02.txt", false)
    val result = Es01And02().resolveMap(rawInput)
    println("part1=${result.first} part2=${result.second}")
}

class Es01And02 {

    fun resolveMap(grid: List<String>): Pair<Int, Int> {
        val resultPart1 = findAntinodes(grid)
        val resultPart2 = findAntinodes(grid, true)
        return resultPart1.size to resultPart2.size
    }

    private fun findAntinodes(
        grid: List<String>,
        enablePart2: Boolean = false
    ): Set<Pair<Int, Int>> {
        val antinodes = mutableSetOf<Pair<Int, Int>>()
        val antennas = mutableMapOf<Char, List<Pair<Int, Int>>>()
        val maxI = grid.lastIndex
        for ((i, line) in grid.withIndex()) {
            val maxJ = line.lastIndex
            for ((j, char) in line.withIndex()) {
                if (char != '.') {
                    val savedAntennas = antennas[char]
                    if (savedAntennas == null) {
                        antennas[char] = listOf(i to j)
                    } else {
                        antinodes.addAll(calculateAntinodes(savedAntennas, i to j, maxI, maxJ, enablePart2))
                        antennas[char] = savedAntennas + listOf(i to j)
                    }

                }
            }
        }
        if (enablePart2) {
            antennas.forEach { (_, v) -> if (v.size > 1) antinodes.addAll(v) }
        }
        return antinodes
    }

    @Suppress("unused")
    private fun printGrid(grid: List<String>, antinodes: Collection<Pair<Int, Int>>) {
        for ((i, line) in grid.withIndex()) {
            for ((j, char) in line.withIndex()) {
                if (char != '.') {
                    print(char)
                } else {
                    if (antinodes.contains(i to j)) {
                        print('#')
                    } else {
                        print(char)
                    }
                }
            }
            println()
        }
        println()
    }

    private fun calculateAntinodes(
        savedAntennas: List<Pair<Int, Int>>,
        antennaToCheck: Pair<Int, Int>,
        maxI: Int,
        maxJ: Int,
        enablePart2: Boolean
    ): Collection<Pair<Int, Int>> {
        val antinodes = mutableListOf<Pair<Int, Int>>()
        for (antenna in savedAntennas) {
            if (isCreateAntinodes(antenna, antennaToCheck)) {
                val elements = createAntinodes(antenna, antennaToCheck, maxI, maxJ, enablePart2)
                antinodes.addAll(elements)
            }
        }
        return antinodes
    }

    private fun createAntinodes(
        antenna: Pair<Int, Int>,
        antennaToCheck: Pair<Int, Int>,
        maxI: Int,
        maxJ: Int,
        enablePart2: Boolean,
    ): Collection<Pair<Int, Int>> {
        val stepI = antennaToCheck.first - antenna.first
        val stepJ = antennaToCheck.second - antenna.second
        val list = mutableListOf<Pair<Int, Int>>()

        var antinodeA = antenna.first - stepI to antenna.second - stepJ

        while (antinodeA.first in 0..maxI && antinodeA.second in 0..maxJ) {
            list.add(antinodeA)
            if (!enablePart2) {
                break
            }
            antinodeA = antinodeA.first - stepI to antinodeA.second - stepJ
        }

        var antinodeB = antennaToCheck.first + stepI to antennaToCheck.second + stepJ

        while (antinodeB.first in 0..maxI && antinodeB.second in 0..maxJ) {
            list.add(antinodeB)
            if (!enablePart2) {
                break
            }
            antinodeB = antinodeB.first + stepI to antinodeB.second + stepJ
        }


        return list
    }

    private fun isCreateAntinodes(antenna: Pair<Int, Int>, antennaToCheck: Pair<Int, Int>): Boolean {
        return (antenna.first - antennaToCheck.first != 0 && antenna.second - antennaToCheck.second != 0) ||
                (antenna.first - antennaToCheck.first == 0 && abs(antenna.second - antennaToCheck.second) > 0) ||
                (antenna.second - antennaToCheck.second == 0 && abs(antenna.first - antennaToCheck.first) > 0) ||
                (abs(antenna.second - antennaToCheck.second) > 1) ||
                (abs(antenna.first - antennaToCheck.first) > 1)
    }
}
