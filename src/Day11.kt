fun main() {

    val memo = List(76) { mutableMapOf<Long, Long>() }

    fun part1and2(stone: Long, blinks: Int): Long {
        if (blinks == 0) return 1
        if (stone in memo[blinks]) {
            return memo[blinks][stone]!!
        }
        var numStones = 0L
        when {
            stone == 0L -> numStones += part1and2(1, blinks - 1)
            lengthOfLong(stone) % 2 == 0 -> {
                val (firstHalf, secondHalf) = splitLongIntoTwoHalves(stone)
                numStones += part1and2(firstHalf, blinks - 1) + part1and2(secondHalf, blinks - 1)
            }

            else -> numStones += part1and2(stone * 2024, blinks - 1)
        }
        memo[blinks][stone] = numStones
        return numStones
    }

    val input = readInputAsOneLine("Day11").split(" ").map { it.toLong() }
    println(input.sumOf { part1and2(it, 25) })
    println(input.sumOf { part1and2(it, 75) })
}