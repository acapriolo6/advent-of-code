package aoc_2024.day_09

import common.readLines


fun main() {
    val rawInput = readLines("${Es01And02::class.java.packageName.replace(".", "/")}/input_02.txt", false)
    val result = Es01And02().calculateCheckSum(rawInput)
    println("part1=${result.first} part2=${result.second}")
}

class Es01And02 {

    fun calculateCheckSum(input: List<String>) : Pair<Long, Long> {
        val resultPart1 = calculateCheckSumPart1(input.first())
        val resultPart2 = calculateCheckSumPart2(input.first())
        return resultPart1 to resultPart2
    }


    private fun calculateChecksumFromMemory(memory: List<MemorySlot>) : Long {
        var result = 0L
        for ((i, el) in memory.withIndex()) {
            if (!el.free) {
                result += i * el.id
            }
        }
        return result
    }
    private fun calculateCheckSumPart1(input: String) : Long {
        val memory = createMemory(input)

        optimizeForPart1(memory)

        return calculateChecksumFromMemory(memory)
    }
    private fun calculateCheckSumPart2(input: String) : Long {
        val memory = createMemory(input)

        optimizeForPart2(memory)

        return calculateChecksumFromMemory(memory)
    }

    private fun createMemory(input: String): MutableList<MemorySlot> {
        val memory = mutableListOf<MemorySlot>()
        var count = 0L
        for ((i, char) in input.withIndex()) {
            val isFreeMemory = i % 2 != 0
            val size = char.digitToInt()
            val index = if (isFreeMemory) -1 else count
            for (j in 0 until size) {
                memory.add(MemorySlot(index, isFreeMemory))
            }
            if (!isFreeMemory) {
                count++
            }
        }
        return memory
    }

    private fun optimizeForPart1(memory: List<MemorySlot>) {
        var lastFileIndex = findNextFile(memory, memory.lastIndex)
        var firstFreeMemorySlotIndex = findNextFreeSpot(memory, 0)
        while (lastFileIndex >= firstFreeMemorySlotIndex) {
            val freeSpace = memory[firstFreeMemorySlotIndex]
            val file = memory[lastFileIndex]
            if (freeSpace.free && !file.free) {
                freeSpace.free = false
                freeSpace.id = file.id
                file.id = -1
                file.free = true
            }
            lastFileIndex = findNextFile(memory, lastFileIndex)
            firstFreeMemorySlotIndex = findNextFreeSpot(memory, firstFreeMemorySlotIndex)
        }
    }

    private fun optimizeForPart2(memory: List<MemorySlot>) {
        var nextFile = findNextFileAndSize(memory, memory.lastIndex)
        var freeMemory = findNextFreeSpotAndSize(memory, 0)
        while (nextFile.first >= freeMemory.first) {
            while (nextFile.first >= freeMemory.first && nextFile.second > freeMemory.second) {
                freeMemory = findNextFreeSpotAndSize(memory, freeMemory.first + freeMemory.second)
            }
            if (nextFile.first >= freeMemory.first) {
                for (i in 0 until  nextFile.second) {
                    val file = memory[nextFile.first - i]
                    val freeSpace = memory[freeMemory.first + i]
                    if (freeSpace.free && !file.free) {
                        freeSpace.free = false
                        freeSpace.id = file.id
                        file.id = -1
                        file.free = true
                    }
                }
            }

            nextFile = findNextFileAndSize(memory, nextFile.first - nextFile.second)
            freeMemory = findNextFreeSpotAndSize(memory, 0)
        }
    }

    private fun findNextFile(memory: List<MemorySlot>, lastFileIndex: Int): Int {
        for (i in lastFileIndex downTo 0) {
            if (!memory[i].free) return i
        }
        throw RuntimeException("Next file not found")
    }

    private fun findNextFileAndSize(memory: List<MemorySlot>, lastFileIndex: Int): Pair<Int, Int> {
        var id = -1L
        var count = 0
        var firstIndex : Int? = null
        for (i in lastFileIndex downTo 0) {
            val slot = memory[i]
            if (!slot.free && firstIndex == null) {
                firstIndex = i
                id = slot.id
            }
            if (firstIndex != null) {
                if (slot.free || slot.id != id) {
                    return firstIndex to count
                }
                count++
            }
        }
        return 0 to 0
    }

    private fun findNextFreeSpotAndSize(memory: List<MemorySlot>, firstFreeMemorySlotIndex: Int): Pair<Int, Int> {
        var count = 0
        var firstIndex : Int? = null
        for (i in firstFreeMemorySlotIndex .. memory.lastIndex) {
            val slot = memory[i]
            if (slot.free && firstIndex == null) {
                firstIndex = i
            }
            if (firstIndex != null) {
                if (!slot.free || i == memory.lastIndex) {
                    return firstIndex to count
                }
                count++
            }
        }
        return memory.lastIndex to 0
    }

    private fun findNextFreeSpot(memory: List<MemorySlot>, firstFreeMemorySlotIndex: Int): Int {
        for (i in firstFreeMemorySlotIndex .. memory.lastIndex) {
            if (memory[i].free) return i
        }
        throw RuntimeException("Next free spot not found")
    }

    class MemorySlot (
        var id: Long = -1,
        var free: Boolean,
    )

}
