package aoc_2024.day_06

import common.readLines
import common.replaceCharAt


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_02.txt", false)
    val result = Es01And02().resolveMap(rawInput, '#')
    println("part1=${result.first} part2=${result.second}")
}

class Es01And02 {

    private fun turn(currentDirection: Direction, degrees: Int): Direction {
        if (degrees == 90) {
            return when (currentDirection) {
                Direction.UP -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
            }
        }
        if (degrees == -90) {
            return when (currentDirection) {
                Direction.UP -> Direction.LEFT
                Direction.RIGHT -> Direction.UP
                Direction.DOWN -> Direction.RIGHT
                Direction.LEFT -> Direction.DOWN
            }
        }
        throw RuntimeException("invalid degrees")
    }

    private fun getStartInfo(grid: List<String>, obstacle: Char): Pair<Pair<Int, Int>, Direction> {
        for ((i, line) in grid.withIndex()) {
            for ((j, char) in line.withIndex()) {
                if (char != '.' && char != obstacle) {
                    println("Found starting position at i=$i j=$j direction=$char")
                    return i to j to Direction.fromSymbol("$char")
                }
            }
        }
        throw RuntimeException("Starting point not found")
    }

    fun resolveMap(grid: List<String>, obstacle: Char): Pair<Int, Int> {
        val startInfo = getStartInfo(grid, obstacle)
        val resultPart1 = resolveMap(grid, obstacle, startInfo.first, startInfo.second)
        val resultPart2 = resolveMapPart2(grid, obstacle, startInfo.first, startInfo.second, resultPart1.first)
        return resultPart1.first.size to resultPart2
    }

    private fun resolveMapPart2(
        grid: List<String>,
        obstacle: Char,
        startPosition: Pair<Int, Int>,
        startDirection: Direction,
        visited: Set<Pair<Int, Int>>
    ): Int {
        var count = 0
        for (pair in visited.filterNot { it == startPosition }) {
            val newGrid = addObstacle(grid, pair, obstacle)
            if (resolveMap(newGrid, obstacle, startPosition, startDirection).second) {
                count++
            }
        }

        return count
    }

    private fun addObstacle(grid: List<String>, position: Pair<Int, Int>, obstacle: Char): List<String> {
        val newGrid = grid.toMutableList()
        newGrid[position.first] = grid[position.first].replaceCharAt(position.second, obstacle)
        return newGrid
    }


    private fun resolveMap(
        grid: List<String>,
        obstacle: Char,
        startPositionInput: Pair<Int, Int>,
        startDirectionInput: Direction
    ): Pair<Set<Pair<Int, Int>>, Boolean> {
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
        var currentPosition = startPositionInput
        var currentDirection = startDirectionInput
        var prevPosition = currentPosition

        while (currentPosition.first in 0..grid.lastIndex && currentPosition.second in 0..grid.first().lastIndex) {
            if (visited.contains(currentPosition to currentDirection)) {
                return visited.map { it.first }.toSet() to true
            }

            if (grid[currentPosition.first][currentPosition.second] == obstacle) {
                currentDirection = turn(currentDirection, 90)
                currentPosition = prevPosition
            }

            val nextPosition = calculateNextPosition(currentPosition, currentDirection)
            visited.add(currentPosition to currentDirection)
            prevPosition = currentPosition
            currentPosition = nextPosition
        }


        return visited.map { it.first }.toSet() to false
    }

    private fun calculateNextPosition(currentPosition: Pair<Int, Int>, currentDirection: Direction): Pair<Int, Int> {
        return when (currentDirection) {
            Direction.UP -> currentPosition.first - 1 to currentPosition.second
            Direction.DOWN -> currentPosition.first + 1 to currentPosition.second
            Direction.RIGHT -> currentPosition.first to currentPosition.second + 1
            Direction.LEFT -> currentPosition.first to currentPosition.second - 1
        }
    }
}

enum class Direction(private val symbol: String) {
    UP("^"), DOWN("v"), LEFT("<"), RIGHT(">");

    companion object {
        fun fromSymbol(symbol: String): Direction {
            return entries.first { it.symbol == symbol }
        }
    }
}
